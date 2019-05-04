package com.hch.hooney.musiccloudproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import com.hch.hooney.musiccloudproject.R;
import com.hch.hooney.musiccloudproject.views.CategoryView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private LinearLayout main_parent;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        main_parent = view.findViewById(R.id.home_parent_view);

        CategoryView view1 = new CategoryView(getContext());
        view1.setViewData("Chill", "Popular playlists from the SoundCloud.");
        main_parent.addView(view1);

        CategoryView view2 = new CategoryView(getContext());
        view2.setViewData("Chill2", "Popular playlists from the SoundCloud.");
        main_parent.addView(view2);

        return view;
    }

}
