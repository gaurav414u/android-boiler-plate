package com.gauravbhola.androidboilerplate.di;


import com.gauravbhola.androidboilerplate.BoilerPlateApp;
import com.gauravbhola.androidboilerplate.ui.MainActivity;
import com.gauravbhola.androidboilerplate.di.module.ActivityBindingModule;
import com.gauravbhola.androidboilerplate.di.module.AppModule;
import com.gauravbhola.androidboilerplate.di.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules= {AppModule.class, NetModule.class, ActivityBindingModule.class})
public interface AppComponent {
    public void inject(BoilerPlateApp app);

    public void inject(MainActivity activity);
}
