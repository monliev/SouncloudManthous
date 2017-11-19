package com.infotechnodev.scmanthous.ui.stream;

import com.infotechnodev.scmanthous.model.Stream;

import java.util.List;

/**
 * @author Niels Masdorp (NielsMasdorp)
 */
public interface OnStreamServiceListener {

    void streamStopped();

    void updateTimerValue(String timeLeft);

    void restoreUI(Stream stream, boolean isPlaying);

    void setLoading();

    void streamPlaying();

    void animateTo(Stream currentStream);

    void error(String string);

    void showAllStreams(List<Stream> streams);
}
