package com.gauravbhola.androidboilerplate;

import com.gauravbhola.androidboilerplate.di.DaggerAppComponent;
import com.gauravbhola.androidboilerplate.di.module.AppModule;
import com.gauravbhola.androidboilerplate.di.module.NetModule;
import com.gauravbhola.androidboilerplate.util.Const;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class BoilerPlateApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;

    @Override
    public void onCreate() {
        DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Const.BASE_URL))
                .build()
                .inject(this);
        super.onCreate();
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityInjector;
    }
}
