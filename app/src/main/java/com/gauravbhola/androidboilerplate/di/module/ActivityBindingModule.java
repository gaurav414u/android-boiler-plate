package com.gauravbhola.androidboilerplate.di.module;


import com.gauravbhola.androidboilerplate.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract MainActivity contributesAndroidInjector();

}
