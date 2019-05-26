package com.hch.hooney.musiccloudproject.views;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hch.hooney.musiccloudproject.R;

public class HiddenPanelView extends LinearLayout {
    private static final String TAG = HiddenPanelView.class.getSimpleName();
    private ImageView mainImage, downBtn;
    private ImageButton  musicListBtn, talkBtn, menuBtn;
    private TextView music_title, music_singer;
    private SeekBar seekbar;


    private OnTalkButtonClickListener listener;
    private OnSeekBarClickListener seekListener;

    public void setOnTalkButtonClickListener(OnTalkButtonClickListener listener){
        this.listener = listener;
    }

    public interface OnTalkButtonClickListener{
        void onClick(ImageButton talkBtn, SeekBar seekBar);
    }

    public void setOnSeekBarClickListenr(OnSeekBarClickListener listener){
        this.seekListener = listener;
    }

    public interface OnSeekBarClickListener{
        void onProgress(SeekBar seekBar, int progress, boolean fromUser);
    }

    public HiddenPanelView(Context context) {
        super(context);

        setView(context);
    }

    private void setView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.hidden_layout_panel_view, this, false);
        mainImage = view.findViewById(R.id.hidden_layout_panel_background_image);
        music_title = view.findViewById(R.id.hidden_layout_panel_title);
        music_singer = view.findViewById(R.id.hidden_layout_panel_singer);
        seekbar = view.findViewById(R.id.hidden_layout_panel_seek_bar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(seekListener != null){
                    seekListener.onProgress(seekBar, progress, fromUser);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        downBtn = view.findViewById(R.id.hidden_layout_panel_down_btn);
        musicListBtn = view.findViewById(R.id.hidden_layout_panel_music_list);
        talkBtn = view.findViewById(R.id.hidden_layout_panel_talk);
        menuBtn = view.findViewById(R.id.hidden_layout_panel_menu);
        addView(view);
        setEvents();
    }

    private void setEvents() {
        talkBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(talkBtn, seekbar);
                }
            }
        });
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

    public void setMaxDuration(int max){
        if(seekbar.getMax() != max){
            seekbar.setMax(max);
        }
    }

    public void setSeekProgress(int progress){
        seekbar.setProgress(progress);
    }

    public int getSeekProgress(){
        return seekbar.getProgress();
    }
}
