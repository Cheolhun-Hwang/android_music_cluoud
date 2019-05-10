package com.hch.hooney.musiccloudproject.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hch.hooney.musiccloudproject.Do.CategoryDo;
import com.hch.hooney.musiccloudproject.R;
import com.hch.hooney.musiccloudproject.listPack.CategoryAdapter;

import java.util.ArrayList;

public class CategoryView extends LinearLayout {
    private Context context;
    private TextView category_title;
    private TextView category_description;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private OnClickListItemListener listener;

    public void setOnClickListItemListener(final OnClickListItemListener listItemListener){
        this.listener = listItemListener;
    }

    public interface OnClickListItemListener{
        void onItemSelected(View view, CategoryDo item);
    }

    public CategoryView(Context context) {
        super(context);
        this.context = context;
        setView(context);
    }

    private void setView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.home_category_view, this, false);
        category_title = view.findViewById(R.id.category_title);
        category_description = view.findViewById(R.id.category_description);
        recyclerView = view.findViewById(R.id.category_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        this.addView(view);
    }

    private ArrayList<CategoryDo> tempList(){
        ArrayList<CategoryDo> temp = new ArrayList<>();
        for(int i = 0 ; i < 10; i++){
            CategoryDo item = new CategoryDo();
            item.setC_id("cid_"+i);
            item.setC_title("Title "+i);
            item.setC_singer("Auth "+i);
            temp.add(item);
        }
        return temp;
    }

    public void setViewData(String title, String des){
        category_title.setText(title);
        category_description.setText(des);
        adapter = new CategoryAdapter(tempList(), context);
        adapter.setOnClickViewListener(new CategoryAdapter.onClickViewListener() {
            @Override
            public void onClicked(View view, CategoryDo item) {
                Toast.makeText(context, "Click : " + item.getC_id(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void setViewData(String title, String des, ArrayList<CategoryDo> list){
        category_title.setText(title);
        category_description.setText(des);
        adapter = new CategoryAdapter(list, context);
        adapter.setOnClickViewListener(new CategoryAdapter.onClickViewListener() {
            @Override
            public void onClicked(View view, CategoryDo item) {
                if(listener != null){
                    listener.onItemSelected(view, item);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

}
