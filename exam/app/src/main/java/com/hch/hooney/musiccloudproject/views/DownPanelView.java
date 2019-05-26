package com.hch.hooney.musiccloudproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hch.hooney.musiccloudproject.R;

public class DownPanelView extends LinearLayout {
    private static final String TAG = DownPanelView.class.getSimpleName();
    private ImageButton playBtn;
    private TextView musicTitle, musicSinger;
    private boolean isPlay;
    private OnPlayButtonClickListener listener;
    private String audioUrl;

    public void setOnPlayButtonClickListener(OnPlayButtonClickListener listener){
        this.listener = listener;
    }

    public interface OnPlayButtonClickListener{
        void onClicked(ImageButton button, boolean isPlay, String audioUrl);
    }

    public DownPanelView(Context context) {
        super(context);
        isPlay = false;
        setView(context);
    }

    private void setView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.down_music_panel_view, this, false);
        playBtn = view.findViewById(R.id.down_music_panel_play_btn);
        playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlay = !isPlay;
                if(listener != null){
                    listener.onClicked(playBtn, isPlay, audioUrl);
                }
            }
        });
        musicTitle = view.findViewById(R.id.down_music_panel_play_title);
        musicSinger = view.findViewById(R.id.down_music_panel_play_singer);
        addView(view);
    }

    public void setViewData(String title, String singer, String url){
        musicTitle.setText(title);
        musicSinger.setText(singer);
        this.audioUrl = url;
    }
}
