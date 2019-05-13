package com.hch.hooney.musiccloudproject.Services.Binds;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;

import java.io.IOException;

public class AudioServiceBinder extends Binder implements MediaPlayer.OnPreparedListener {
    private static final String TAG = AudioServiceBinder.class.getSimpleName();
    public static final int UPDATE_AUDIO_PROGRESS = 1;

    private String audioUrl;
    private MediaPlayer mediaPlayer;
    private Context context;
    private Handler progressUpdateHandler;

    public Context getMusicContext() {
        return context;
    }

    public void setMusicContext(Context context) {
        this.context = context;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public Handler getProgressUpdateHandler() {
        return progressUpdateHandler;
    }

    public void setProgressUpdateHandler(Handler progressUpdateHandler) {
        this.progressUpdateHandler = progressUpdateHandler;
    }

    private void initMediaPlayer(){
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); // Deprecated
        } //Audio Streaming > Firebase
    }

    public void startMusic(){
        if(this.mediaPlayer == null){
            initMediaPlayer();
        }
        try{
            mediaPlayer.setDataSource(this.audioUrl);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic(){
        if(this.mediaPlayer != null){
            this.mediaPlayer.stop();
            destroyMusic();
        }
    }

    public void pauseMusic(){
        if(this.mediaPlayer != null){
            this.mediaPlayer.pause();
        }
    }

    public void destroyMusic(){
        if(this.mediaPlayer != null){
            if(this.mediaPlayer.isPlaying()){
                this.mediaPlayer.stop();
            }
            this.mediaPlayer.release();
//            this.mediaPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
