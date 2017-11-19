package com.infotechnodev.scmanthous;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.infotechnodev.scmanthous.di.AppModule;
import com.infotechnodev.scmanthous.di.ApplicationComponent;
import com.infotechnodev.scmanthous.di.DaggerApplicationComponent;
import com.infotechnodev.scmanthous.di.InteractorModule;
import com.infotechnodev.scmanthous.di.MainModule;
import com.infotechnodev.scmanthous.di.NetworkModule;
import com.infotechnodev.scmanthous.di.StorageModule;
import com.infotechnodev.scmanthous.ui.stream.MainView;

import io.fabric.sdk.android.Fabric;

/**
 * @author Niels Masdorp (NielsMasdorp)
 */
public class SleeplyApplication extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
    }

    public ApplicationComponent provideApplicationComponent(MainView view) {

        applicationComponent = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .storageModule(new StorageModule())
                .networkModule(new NetworkModule())
                .interactorModule(new InteractorModule())
                .mainModule(new MainModule(view))
                .build();

        return applicationComponent;
    }
}