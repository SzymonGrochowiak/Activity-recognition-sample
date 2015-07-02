package com.activityrecognitiondemo.events;

import com.google.android.gms.location.ActivityRecognitionResult;

/**
 * @author Szymon Grochowiak
 */
public class ActivityRecognitionEvent {

    private ActivityRecognitionResult mResult;

    public ActivityRecognitionEvent(ActivityRecognitionResult result) {
        mResult = result;
    }

    public ActivityRecognitionResult getResult() {
        return mResult;
    }
}
