package com.hch.hooney.musiccloudproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hch.hooney.musiccloudproject.R;

public class DownPanelView extends LinearLayout {
    private TextView music_title, music_singer;
    private ImageButton music_play;
    private boolean isPlay;

    public void setOnPlayButtonClickListener(final OnPlayButtonClickListener listener){
        music_play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClicked(isPlay, music_play);
                }
            }
        });
    }

    public interface OnPlayButtonClickListener{
        void onClicked(boolean isPlay, ImageButton btn);
    }
    public DownPanelView(Context context) {
        super(context);
        isPlay = false;
        setView(context);
    }

    private void setView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.down_music_panel_view, this, false);
        music_title = view.findViewById(R.id.down_music_panel_play_title);
        music_singer = view.findViewById(R.id.down_music_panel_play_singer);
        music_play = view.findViewById(R.id.down_music_panel_play_btn);
        addView(view);
    }

    public void setMusicInfo(String title, String singer){
        this.music_title.setText(title);
        this.music_singer.setText(singer);
    }
}
