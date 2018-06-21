package com.example.maguilar.shptes;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by maguilar on 20/06/2018.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SystemClock.sleep(3000);
    }
}
