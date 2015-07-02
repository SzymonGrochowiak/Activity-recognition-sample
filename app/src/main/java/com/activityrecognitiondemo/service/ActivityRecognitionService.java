package com.activityrecognitiondemo.service;

import android.app.IntentService;
import android.content.Intent;

import com.activityrecognitiondemo.MainThreadBus;
import com.activityrecognitiondemo.events.ActivityRecognitionEvent;
import com.google.android.gms.location.ActivityRecognitionResult;

/**
 * @author Szymon Grochowiak
 */
public class ActivityRecognitionService extends IntentService {

    private MainThreadBus mBus = MainThreadBus.getInstance();

    public ActivityRecognitionService() {
        super("ActivityRecognitionService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ActivityRecognitionService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            mBus.post(new ActivityRecognitionEvent(result));
        }
    }
}
