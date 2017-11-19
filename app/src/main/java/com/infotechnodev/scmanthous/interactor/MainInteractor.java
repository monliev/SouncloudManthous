package com.infotechnodev.scmanthous.interactor;


import com.infotechnodev.scmanthous.model.Stream;
import com.infotechnodev.scmanthous.ui.stream.OnStreamServiceListener;

/**
 * @author Niels Masdorp (NielsMasdorp)
 */
public interface MainInteractor {

    void startService(OnStreamServiceListener listener);

    void unbindService();

    void playStream();

    void nextStream();

    void previousStream();

    void setSleepTimer(int ms);

    void getAllStreams();

    void streamPicked(Stream stream);

    boolean isStreamWifiOnly();

    void setStreamWifiOnly(boolean checked);
}
