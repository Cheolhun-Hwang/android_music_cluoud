package com.hch.hooney.musiccloudproject.views;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hch.hooney.musiccloudproject.R;

public class HiddenPanelView2 extends LinearLayout {
    private static final String TAG = HiddenPanelView2.class.getSimpleName();
    private ImageView mainImage, downBtn;
    private TextView music_title, music_singer;
    private AppCompatSeekBar seekbar;


    public HiddenPanelView2(Context context) {
        super(context);

        setView(context);
    }

    private void setView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.hidden_layout_panel_view, this, false);
        mainImage = view.findViewById(R.id.hidden_layout_panel_background_image);
        music_title = view.findViewById(R.id.hidden_layout_panel_title);
        music_singer = view.findViewById(R.id.hidden_layout_panel_singer);
        seekbar = view.findViewById(R.id.hidden_layout_panel_seek_bar);
        downBtn = view.findViewById(R.id.hidden_layout_panel_down_btn);
        addView(view);
    }

    public void setViewData(String url){
        Glide.with(getContext())
                .load(url)
                .centerCrop()
                .into(mainImage);
    }

    public void setViewData(String title, String singer, String url){
        music_title.setText(title);
        music_singer.setText(singer);
        Glide.with(getContext())
                .load(url)
                .centerCrop()
                .into(mainImage);
    }

    public void setSeekProgress(int progress){
        seekbar.setProgress(progress);
    }

    public int getSeekProgress(){
        return seekbar.getProgress();
    }
}
