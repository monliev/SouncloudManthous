package com.infotechnodev.scmanthous.interactor;

import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.infotechnodev.scmanthous.R;
import com.infotechnodev.scmanthous.model.Stream;
import com.infotechnodev.scmanthous.service.StreamService;
import com.infotechnodev.scmanthous.service.StreamService.State;
import com.infotechnodev.scmanthous.ui.stream.OnStreamServiceListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Niels Masdorp (NielsMasdorp)
 */
public class MainInteractorImpl implements MainInteractor {

    private final static String TAG = MainInteractorImpl.class.getSimpleName();
    private final static String LAST_STREAM_IDENTIFIER = "last_stream_identifier";
    private final static String STREAM_WIFI_ONLY = "stream_wifi_only";

    private Application application;
    private SharedPreferences preferences;
    private ConnectivityManager connectivityManager;

    private StreamService streamService;
    private OnStreamServiceListener presenter;

    private Boolean boundToService = false;

    private List<Stream> streams;
    private Stream currentStream;

    public MainInteractorImpl(Application application, SharedPreferences preferences, ConnectivityManager connectivityManager) {

        this.application = application;
        this.preferences = preferences;
        this.connectivityManager = connectivityManager;

        streams = new ArrayList<>();
        streams.add(new Stream(0, "https://api.soundcloud.com/tracks/228911516/stream",
                application.getString(R.string.judul_1), application.getString(R.string
                .deskripsi_1), R.drawable.background, R.drawable.no_1));
        streams.add(new Stream(1, "https://api.soundcloud.com/tracks/228911513/stream",
                application.getString(R.string.judul_2), application.getString(R.string
                .deskripsi_2), R.drawable.background, R.drawable.no_2));
        streams.add(new Stream(2, "https://api.soundcloud.com/tracks/228911511/stream",
                application.getString(R.string.judul_3), application.getString(R.string
                .deskripsi_3), R.drawable.background, R.drawable.no_3));
        streams.add(new Stream(3, "https://api.soundcloud.com/tracks/228911510/stream",
                application.getString(R.string.judul_4), application.getString(R.string
                .deskripsi_4), R.drawable.background, R.drawable.no_4));
        streams.add(new Stream(4, "https://api.soundcloud.com/tracks/228911507/stream",
                application.getString(R.string.judul_5), application.getString(R.string
                .deskripsi_5), R.drawable.background, R.drawable.no_5));
        streams.add(new Stream(5, "https://api.soundcloud.com/tracks/228911504/stream",
                application.getString(R.string.judul_6), application.getString(R.string
                .deskripsi_6), R.drawable.background, R.drawable.no_6));
        streams.add(new Stream(6, "https://api.soundcloud.com/tracks/228911502/stream",
                application.getString(R.string.judul_7), application.getString(R.string
                .deskripsi_7), R.drawable.background, R.drawable.no_7));
        streams.add(new Stream(7, "https://api.soundcloud.com/tracks/228911496/stream",
                application.getString(R.string.judul_8), application.getString(R.string
                .deskripsi_8), R.drawable.background, R.drawable.no_8));
        streams.add(new Stream(8, "https://api.soundcloud.com/tracks/228911493/stream",
                application.getString(R.string.judul_9), application.getString(R
                .string.deskripsi_9), R.drawable.background, R.drawable.no_9));
        streams.add(new Stream(9, "https://api.soundcloud.com/tracks/228911490/stream",
                application.getString(R.string.judul_10), application.getString(R
                .string.deskripsi_10), R.drawable.background, R.drawable.no_10));
        streams.add(new Stream(10, "https://api.soundcloud.com/tracks/228911488/stream",
                application.getString(R.string.judul_11), application.getString(R
                .string.deskripsi_11), R.drawable.background, R.drawable.no_11));
        streams.add(new Stream(11, "https://api.soundcloud.com/tracks/228911484/stream",
                application.getString(R.string.judul_12), application.getString(R
                .string.deskripsi_12), R.drawable.background, R.drawable.no_12));
        streams.add(new Stream(12, "https://api.soundcloud.com/tracks/228911479/stream",
                application.getString(R.string.judul_13), application.getString
                (R.string.deskripsi_13), R.drawable.background, R.drawable.no_13));
        streams.add(new Stream(13, "https://api.soundcloud.com/tracks/228911475/stream",
                application.getString(R.string.judul_14), application.getString(R
                .string.deskripsi_14), R.drawable.background, R.drawable.no_14));
        streams.add(new Stream(14, "https://api.soundcloud.com/tracks/228911472/stream",
                application.getString(R.string.judul_15), application.getString(R
                .string.deskripsi_15), R.drawable.background, R.drawable.no_15));
        streams.add(new Stream(15, "https://api.soundcloud.com/tracks/228911470/stream",
                application.getString(R.string.judul_16), application.getString(R
                .string.deskripsi_16), R.drawable.background, R.drawable.no_16));
        streams.add(new Stream(16, "https://api.soundcloud.com/tracks/228911469/stream",
                application.getString(R.string.judul_17), application.getString(R
                .string.deskripsi_17), R.drawable.background, R.drawable.no_17));
        streams.add(new Stream(17, "https://api.soundcloud.com/tracks/228911468/stream",
                application.getString(R.string.judul_18), application.getString(R
                .string.deskripsi_18), R.drawable.background, R.drawable.no_18));
        streams.add(new Stream(18, "https://api.soundcloud.com/tracks/228911463/stream",
                application.getString(R.string.judul_19), application.getString(R
                .string.deskripsi_19), R.drawable.background, R.drawable.no_19));
        streams.add(new Stream(19, "https://api.soundcloud.com/tracks/228911461/stream",
                application.getString(R.string.judul_20), application.getString(R
                .string.deskripsi_20), R.drawable.background, R.drawable.no_20));
    }

    @Override
    public void startService(OnStreamServiceListener presenter) {

        this.presenter = presenter;

        Intent intent = new Intent(application, StreamService.class);
        if (!isServiceAlreadyRunning()) {
            Log.i(TAG, "onStart: service not running, starting service.");
            application.startService(intent);
        }

        if (!boundToService) {
            Log.i(TAG, "onStart: binding to service.");
            boundToService = application.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {

        Log.i(TAG, "onStart: registering broadcast receiver.");
        IntentFilter broadcastIntentFilter = new IntentFilter();
        broadcastIntentFilter.addAction(StreamService.STREAM_DONE_LOADING_INTENT);
        broadcastIntentFilter.addAction(StreamService.TIMER_DONE_INTENT);
        broadcastIntentFilter.addAction(StreamService.TIMER_UPDATE_INTENT);
        LocalBroadcastManager.getInstance(application).registerReceiver((broadcastReceiver), broadcastIntentFilter);
    }

    @Override
    public void unbindService() {

        if (boundToService) {
            application.unbindService(serviceConnection);
            boundToService = false;
        }
        LocalBroadcastManager.getInstance(application).unregisterReceiver(broadcastReceiver);

        preferences.edit().putInt(LAST_STREAM_IDENTIFIER, currentStream != null ? currentStream.getId() : 0).apply();
    }

    @Override
    public void playStream() {

        boolean connectedToWifi = checkIfOnWifi();

        switch (streamService.getState()) {
            case STOPPED:
                if (connectedToWifi || !isStreamWifiOnly()) {
                    streamService.playStream(currentStream);
                    presenter.setLoading();
                    if (!connectedToWifi) {
                        presenter.error(application.getString(R.string.no_wifi_toast));
                    }
                } else {
                    presenter.error(application.getString(R.string.no_wifi_setting_toast));
                }
                break;
            case PAUSED:
                if (connectedToWifi || !isStreamWifiOnly()) {
                    streamService.resumeStream();
                    presenter.streamPlaying();
                    if (!connectedToWifi) {
                        presenter.error(application.getString(R.string.no_wifi_toast));
                    }
                } else {
                    presenter.error(application.getString(R.string.no_wifi_setting_toast));
                }
                break;
            case PLAYING:
                streamService.pauseStream();
                presenter.streamStopped();
                break;
        }
    }

    @Override
    public void nextStream() {

        int currentStreamId = currentStream.getId();
        if (currentStreamId != (streams.size() - 1)) {
            currentStream = streams.get(currentStreamId + 1);
        } else {
            currentStream = streams.get(0);
        }

        if (streamService.getState() == State.PLAYING || streamService.getState() == State.PAUSED) {
            streamService.stopStreaming();
            playStream();
        }

        presenter.animateTo(currentStream);
    }

    @Override
    public void previousStream() {

        int currentStreamId = currentStream.getId();
        if (currentStreamId != 0) {
            currentStream = streams.get(currentStreamId - 1);
        } else {
            currentStream = streams.get(streams.size() - 1);
        }

        if (streamService.getState() == State.PLAYING || streamService.getState() == State.PAUSED) {
            streamService.stopStreaming();
            playStream();
        }

        presenter.animateTo(currentStream);
    }

    @Override
    public void setSleepTimer(int option) {

        if (streamService.getState() == State.PLAYING) {
            streamService.setSleepTimer(calculateMs(option));
        } else {
            presenter.error(application.getString(R.string.start_stream_error_toast));
        }
    }

    @Override
    public void getAllStreams() {

        presenter.showAllStreams(streams);
    }

    @Override
    public void streamPicked(Stream stream) {

        if (stream.getId() != currentStream.getId()) {

            currentStream = stream;

            if (streamService.getState() == State.PLAYING || streamService.getState() == State.PAUSED) {
                streamService.stopStreaming();
                playStream();
            }

            presenter.animateTo(currentStream);
        }
    }

    @Override
    public boolean isStreamWifiOnly() {

        return preferences.getBoolean(STREAM_WIFI_ONLY, false);
    }

    @Override
    public void setStreamWifiOnly(boolean checked) {

        if (checked)
            if (((streamService.getState() == State.PLAYING) || (streamService.getState() == State.PAUSED)))
                if (!checkIfOnWifi()) {
                    streamService.stopStreaming();
                    presenter.streamStopped();
                    presenter.error(application.getString(R.string.toast_no_wifi_but_playing));
                }

        preferences.edit().putBoolean(STREAM_WIFI_ONLY, checked).apply();
    }

    /**
     * See if the StreamService is already running in the background.
     *
     * @return boolean indicating if the service runs
     */
    private boolean isServiceAlreadyRunning() {
        ActivityManager manager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (StreamService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles the intents the broadcast receiver receives
     *
     * @param intent
     */
    private void handleIntent(Intent intent) {

        if (intent.getAction().equals(StreamService.STREAM_DONE_LOADING_INTENT)) {
            boolean success = intent.getBooleanExtra(StreamService.STREAM_DONE_LOADING_SUCCESS, false);
            if (!success) {
                presenter.streamStopped();
                presenter.error(application.getString(R.string.stream_error_toast));

            } else {
                presenter.streamPlaying();
            }
        } else if (intent.getAction().equals(StreamService.TIMER_DONE_INTENT)) {
            presenter.streamStopped();
        } else if (intent.getAction().equals(StreamService.TIMER_UPDATE_INTENT)) {
            long timerValue = (long) intent.getIntExtra(StreamService.TIMER_UPDATE_VALUE, 0);
            presenter.updateTimerValue(formatTimer(timerValue));
        }
    }

    private String formatTimer(long timeLeft) {

        if (timeLeft > TimeUnit.HOURS.toMillis(1)) {
            return String.format(Locale.getDefault(), "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(timeLeft),
                    TimeUnit.MILLISECONDS.toMinutes(timeLeft) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeLeft)),
                    TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft)));
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(timeLeft) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeLeft)),
                    TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft)));
        }
    }

    private int calculateMs(int option) {
        switch (option) {
            case 0:
                return 0;
            case 1:
                return (int) TimeUnit.MINUTES.toMillis(15);
            case 2:
                return (int) TimeUnit.MINUTES.toMillis(20);
            case 3:
                return (int) TimeUnit.MINUTES.toMillis(30);
            case 4:
                return (int) TimeUnit.MINUTES.toMillis(40);
            case 5:
                return (int) TimeUnit.MINUTES.toMillis(50);
            case 6:
                return (int) TimeUnit.HOURS.toMillis(1);
            case 7:
                return (int) TimeUnit.HOURS.toMillis(2);
            case 8:
                return (int) TimeUnit.HOURS.toMillis(3);
            default:
                return 0;
        }
    }

    private boolean checkIfOnWifi() {

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() != ConnectivityManager.TYPE_WIFI) {
                return false;
            }
        }
        return true;
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Log.i(TAG, "onServiceConnected: successfully bound to service.");
            StreamService.StreamBinder binder = (StreamService.StreamBinder) service;
            streamService = binder.getService();
            currentStream = streamService.getPlayingStream();
            if (currentStream != null) {
                presenter.restoreUI(currentStream, streamService.getState() == State.PLAYING);
            } else {
                int last = preferences.getInt(LAST_STREAM_IDENTIFIER, 0);
                currentStream = streams.get(last);
                presenter.restoreUI(currentStream, false);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.i(TAG, "onServiceDisconnected: disconnected from service.");
            streamService = null;
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            handleIntent(intent);
        }
    };
}
