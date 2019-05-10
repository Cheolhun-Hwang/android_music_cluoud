package com.hch.hooney.musiccloudproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.hch.hooney.musiccloudproject.R;

public class HiddenPanelView extends LinearLayout {
    private ImageView main_image;
    public HiddenPanelView(Context context) {
        super(context);

        setView(context);
    }

    private void setView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.hidden_layout_panel_view, this, false);
        main_image = view.findViewById(R.id.hidden_layout_panel_background_image);
        addView(view);
    }

    public void setImage(Context context, String url){
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(main_image);
    }
}
