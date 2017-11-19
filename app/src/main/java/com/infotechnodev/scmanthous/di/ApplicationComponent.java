package com.infotechnodev.scmanthous.di;

import com.infotechnodev.scmanthous.ui.stream.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Niels Masdorp (NielsMasdorp)
 */
@Singleton
@Component(modules = { AppModule.class, StorageModule.class, NetworkModule.class, InteractorModule.class, MainModule.class })
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
}
