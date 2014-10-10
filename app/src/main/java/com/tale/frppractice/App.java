package com.tale.frppractice;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by TALE on 10/10/2014.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
