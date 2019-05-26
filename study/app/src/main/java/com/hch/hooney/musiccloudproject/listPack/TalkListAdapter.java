package com.hch.hooney.musiccloudproject.listPack;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hch.hooney.musiccloudproject.Do.TalkData;
import com.hch.hooney.musiccloudproject.R;

import java.util.ArrayList;
import java.util.List;

public class TalkListAdapter extends RecyclerView.Adapter<TalkListAdapter.TalkListHolder>{
    private List<TalkData> list;

    public TalkListAdapter() {
        this.list = new ArrayList<>();
    }

    public TalkListAdapter(List<TalkData> list) {
        this.list = list;
    }

    public void setList(ArrayList<TalkData> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addList(TalkData... list){
        for(TalkData item : list){
            this.list.add(item);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TalkListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_talk, viewGroup, false);
        return new TalkListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TalkListHolder holder, int index) {
        holder.userMsg.setText(list.get(index).getMsg());
        holder.talkInfo.setText(list.get(index).getUserName() + " " + list.get(index).getCreateDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TalkListHolder extends RecyclerView.ViewHolder{
        ImageView userIcon;
        TextView userMsg, talkInfo;

        public TalkListHolder(@NonNull View itemView) {
            super(itemView);
            userIcon = itemView.findViewById(R.id.item_talk_icon);
            userIcon.setBackground(new ShapeDrawable(new OvalShape()));
            userIcon.setClipToOutline(true);
            userMsg = itemView.findViewById(R.id.item_talk_msg);
            talkInfo = itemView.findViewById(R.id.item_talk_info);
        }
    }
}
