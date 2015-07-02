package com.activityrecognitiondemo;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * @author Szymon Grochowiak
 */
public class MainThreadBus extends Bus {

    private static MainThreadBus mInstance;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public static MainThreadBus getInstance() {
        if (mInstance == null) {
            synchronized (MainThreadBus.class) {
                if (mInstance == null) {
                    mInstance = new MainThreadBus();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    MainThreadBus.super.post(event);
                }
            });
        }
    }
}