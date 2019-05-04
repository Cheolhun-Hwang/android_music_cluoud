package com.hch.hooney.musiccloudproject.listPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hch.hooney.musiccloudproject.Do.CategoryDo;
import com.hch.hooney.musiccloudproject.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{
    private ArrayList<CategoryDo> list;
    private onClickViewListener listener;

    public CategoryAdapter(ArrayList<CategoryDo> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_music, viewGroup, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        CategoryDo item = list.get(position);
        holder.music_title.setText(item.getC_title());
        holder.music_auth.setText(item.getC_auth());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        ImageView music_image;
        TextView music_title, music_auth;

        public CategoryHolder(@NonNull final View itemView) {
            super(itemView);

            music_image = itemView.findViewById(R.id.item_music_image);
            music_title = itemView.findViewById(R.id.item_music_title);
            music_auth = itemView.findViewById(R.id.item_music_author);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onClicked(itemView, list.get(position));
                    }
                }
            });
        }
    }

    public interface onClickViewListener{
        void onClicked(View view, CategoryDo item);
    }

    public void setOnClickViewListener(onClickViewListener lis){
        this.listener = lis;
    }
}
