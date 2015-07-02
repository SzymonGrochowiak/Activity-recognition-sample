package com.activityrecognitiondemo.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.activityrecognitiondemo.MainThreadBus;
import com.activityrecognitiondemo.R;
import com.activityrecognitiondemo.events.ActivityRecognitionEvent;
import com.activityrecognitiondemo.service.ActivityRecognitionService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.squareup.otto.Subscribe;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private MainThreadBus mBus = MainThreadBus.getInstance();

    private TextView recognitionTextView;

    private static final long DETECTION_INTERVAL = 2000;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBus.register(this);

        recognitionTextView = (TextView) findViewById(R.id.recognitionTextView);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();

        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Intent intent = new Intent(this, ActivityRecognitionService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mGoogleApiClient, DETECTION_INTERVAL, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Intent intent = new Intent(this, ActivityRecognitionService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mGoogleApiClient, pendingIntent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Subscribe
    public void onActivityRecognitionEvent(ActivityRecognitionEvent event) {
        recognitionTextView.setText(event.getResult().getMostProbableActivity().toString());
    }
}
