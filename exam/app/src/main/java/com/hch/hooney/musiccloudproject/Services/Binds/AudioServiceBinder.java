package com.hch.hooney.musiccloudproject.Services.Binds;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

public class AudioServiceBinder extends Binder implements MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener {
    private static final String TAG = AudioServiceBinder.class.getSimpleName();
    public static final int UPDATE_AUDIO_PROGRESS = 101;
    public static final int PLAY_AUDIO = 102;

    private String audioUrl;
    private MediaPlayer mediaPlayer;
    private Context context;
    private Handler progressUpdateHandler;
    private Thread progressTread;
    private boolean isPlay;

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

    public int getTotalAudioDuration(){
        if(mediaPlayer != null){
            return mediaPlayer.getDuration();
        }else{
            return 0;
        }
    }

    public int getAudioProgress(){
        if(mediaPlayer.getDuration()>0){
//            return ((mediaPlayer.getCurrentPosition() * 100) / mediaPlayer.getDuration());
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
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
        isPlay = false;
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

    private void initThread(){
        destroyThread();
        progressTread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Message msg = new Message();
                    Message playMsg = new Message();
                    playMsg.what = PLAY_AUDIO;
                    msg.what = UPDATE_AUDIO_PROGRESS;
                    if(mediaPlayer != null){
                        msg.obj = mediaPlayer.getDuration();
                    }else{
                        msg.obj = 0;
                    }
                    playMsg.obj = isPlay;
                    progressUpdateHandler.sendMessage(msg);
                    progressUpdateHandler.sendMessage(playMsg);
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void seekMedia(int progress){
        if(mediaPlayer != null){
            mediaPlayer.seekTo(progress);
        }
    }

    public void destroyThread(){
        if(progressTread != null){
            if(progressTread.isAlive()){
                progressTread.interrupt();
            }
            progressTread = null;
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
        isPlay = true;
        initThread();
        progressTread.start();
        mp.start();
    }


    @Override
    public void onSeekComplete(MediaPlayer mp) {
        isPlay = false;
    }
}
