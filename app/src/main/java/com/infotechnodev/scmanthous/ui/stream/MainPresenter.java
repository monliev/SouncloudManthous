package com.infotechnodev.scmanthous.ui.stream;

import com.infotechnodev.scmanthous.model.Stream;

/**
 * @author Niels Masdorp (NielsMasdorp)
 */
public interface MainPresenter {

    void startService();

    void unBindService();

    void playStream();

    void nextStream();

    void previousStream();

    void setSleepTimer(int ms);

    void getAllStreams();

    void streamPicked(Stream stream);

    boolean isStreamWifiOnly();

    void setStreamWifiOnly(boolean checked);
}
