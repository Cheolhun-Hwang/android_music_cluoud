package com.hch.hooney.musiccloudproject.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hch.hooney.musiccloudproject.Applicatoin.MyApp;
import com.hch.hooney.musiccloudproject.Do.CategoryDo;
import com.hch.hooney.musiccloudproject.MainActivity;
import com.hch.hooney.musiccloudproject.R;
import com.hch.hooney.musiccloudproject.views.CategoryView;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private final static String TAG = HomeFragment.class.getSimpleName();
    private LinearLayout main_parent;
    private OnItemClickListener listener;

    public void setOnItenClickListner(OnItemClickListener listner){
        this.listener = listner;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, CategoryDo item);
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        main_parent = view.findViewById(R.id.home_parent_view);
        loadData();
        return view;
    }

    private void loadData() {
        MainActivity.rootRef.child("List").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<CategoryDo> list = new ArrayList<>();
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    CategoryDo node = new CategoryDo();
                    node.setC_id(item.getKey());
                    node.setC_title(item.child("title").getValue().toString());
                    node.setC_singer(item.child("singer").getValue().toString());
                    node.setC_image_url(item.child("image").getValue().toString());
                    node.setC_url(item.child("url").getValue().toString());
                    Log.i(TAG, "node : " + node.toString());
                    list.add(node);
                }

                CategoryView view1 = new CategoryView(getContext());
                view1.setOnClickListItemListener(new CategoryView.OnClickListItemListener() {
                    @Override
                    public void onItemSelected(View view, CategoryDo item) {
                        if(listener!=null){
                            listener.onItemClick(view, item);
                        }
                    }
                });
                view1.setViewData("Chill", "Popular playlists from the SoundCloud.", list);
                main_parent.addView(view1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
