package com.hch.hooney.musiccloudproject.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.hch.hooney.musiccloudproject.Services.Binds.AudioServiceBinder;

public class MusicService extends Service {
    private AudioServiceBinder binder;

    public MusicService() {
        this.binder = new AudioServiceBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
