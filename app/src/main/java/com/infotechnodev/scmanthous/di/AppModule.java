package com.infotechnodev.scmanthous.di;

import android.app.Application;

import com.infotechnodev.scmanthous.SleeplyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Niels Masdorp (NielsMasdorp)
 */
@Module
public class AppModule {

    private SleeplyApplication app;

    public AppModule(SleeplyApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }
}
