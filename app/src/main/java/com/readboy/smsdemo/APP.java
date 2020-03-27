package com.readboy.smsdemo;

import android.app.Application;

public class APP extends Application {
    private static APP application;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

    }

    public static APP getContext() {
        return application;
    }
}