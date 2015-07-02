package com.activityrecognitiondemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * @author Szymon Grochowiak
 */
public class ActivityRecognitionService extends IntentService {

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
    protected void onHandleIntent(Intent intent) {
        Log.i("ACTIVITY RECOGNITION", "response");
    }
}
