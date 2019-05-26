package com.hch.hooney.musiccloudproject.Services.Binds;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

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

    public String getAudioUrl() {
        return audioUrl;
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    public void setDuration(int duration){
        mediaPlayer.seekTo(duration);
    }

    public Handler getProgressUpdateHandler() {
        return progressUpdateHandler;
    }

    public void setProgressUpdateHandler(Handler progressUpdateHandler) {
        this.progressUpdateHandler = progressUpdateHandler;
    }

    private void initMediaPlayer(){
        if(this.mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            if(Build.VERSION.SDK_INT >= 21){
                mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());
            }else{
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            }
        }
    }

    public void setMediaPlayer(Context context, String url){
        this.context = context;
        if(!url.trim().isEmpty()){
            if(this.audioUrl == null){
                //첫 재생 시
                this.audioUrl = url;
                initMediaPlayer();
                startMusic(url);
            }else{
                //이전 곡에 대한 정보가 있을 시
                if(this.audioUrl.equals(url)){
                    //재생하고 있는 곡
                }else{
                    destroyMusic();
                    this.audioUrl = url;
                    initMediaPlayer();
                    startMusic(url);
                }
            }
        }
    }

    private void startMusic(String url) {
        try{
            mediaPlayer.setDataSource(url);
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
            this.mediaPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
