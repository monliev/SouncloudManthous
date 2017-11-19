package com.infotechnodev.scmanthous.di;

import com.bumptech.glide.RequestManager;
import com.infotechnodev.scmanthous.interactor.MainInteractor;
import com.infotechnodev.scmanthous.ui.stream.MainPresenter;
import com.infotechnodev.scmanthous.ui.stream.MainPresenterImpl;
import com.infotechnodev.scmanthous.ui.stream.MainView;
import com.infotechnodev.scmanthous.ui.stream.StreamGridAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Niels Masdorp (NielsMasdorp)
 */
@Module
public class MainModule {

    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter(MainInteractor interactor) {

        return new MainPresenterImpl(view, interactor);
    }

    @Provides
    @Singleton
    public StreamGridAdapter provideStreamGridAdapter(RequestManager glide) {

        return new StreamGridAdapter(glide);
    }
}
