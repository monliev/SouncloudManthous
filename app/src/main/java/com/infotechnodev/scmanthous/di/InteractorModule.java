package com.infotechnodev.scmanthous.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.infotechnodev.scmanthous.interactor.MainInteractor;
import com.infotechnodev.scmanthous.interactor.MainInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Niels Masdorp (NielsMasdorp)
 */
@Module
public class InteractorModule {

    @Provides
    @Singleton
    public MainInteractor provideMainInteractor(Application application, SharedPreferences preferences, ConnectivityManager connectivityManager) {

        return new MainInteractorImpl(application, preferences, connectivityManager);
    }
}
