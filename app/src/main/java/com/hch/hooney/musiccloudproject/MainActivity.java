package com.hch.hooney.musiccloudproject;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hch.hooney.musiccloudproject.Do.CategoryDo;
import com.hch.hooney.musiccloudproject.fragments.HomeFragment;
import com.hch.hooney.musiccloudproject.fragments.SearchFragment;
import com.hch.hooney.musiccloudproject.fragments.UserFragment;
import com.hch.hooney.musiccloudproject.views.DownPanelView;
import com.hch.hooney.musiccloudproject.views.HiddenPanelView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference rootRef = database.getReference("MusicCloud");

    private SlidingUpPanelLayout upPanelLayout;
    private RelativeLayout panelLayout;
    private DownPanelView downPanelView;
    private HiddenPanelView hiddenPanelView;

    private enum Tabs{
        홈, 검색, 유저
    }

    private BottomNavigationView tabView;
    private HomeFragment home;
    private SearchFragment search;
    private UserFragment user;
    private Tabs nowTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBar(getSupportActionBar());
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();

        switch (nowTab){
            case 홈:
                tabView.setSelectedItemId(R.id.main_home);
                break;
            case 검색:
                tabView.setSelectedItemId(R.id.main_search);
                break;
            case 유저:
                tabView.setSelectedItemId(R.id.main_user);
                break;
        }
    }

    private void init() {
        nowTab = Tabs.홈;
        tabView = findViewById(R.id.main_tabs);

        panelLayout = findViewById(R.id.main_panel_layout);
        downPanelView = new DownPanelView(getApplicationContext());
        downPanelView.setOnPlayButtonClickListener(new DownPanelView.OnPlayButtonClickListener() {
            @Override
            public void onClicked(boolean isPlay, ImageButton btn) {
                Toast.makeText(MainActivity.this, "isPlay : " + isPlay, Toast.LENGTH_SHORT).show();
            }
        });
        hiddenPanelView = new HiddenPanelView(getApplicationContext());
        panelLayout.addView(downPanelView);
        panelLayout.addView(hiddenPanelView);

        upPanelLayout = findViewById(R.id.main_up_panel);
        upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        upPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                hiddenPanelView.setAlpha(slideOffset);
                downPanelView.setAlpha(1-slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });

        home = new HomeFragment();
        home.setOnItenClickListner(new HomeFragment.OnItemClickListener() {
            @Override
            public void onItemClick(View view, CategoryDo item) {
                Toast.makeText(MainActivity.this, "item : " + item.getC_title(), Toast.LENGTH_SHORT).show();
                downPanelView.setMusicInfo(item.getC_title(), item.getC_singer());
                hiddenPanelView.setImage(getApplicationContext(), item.getC_image_url());
                upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        search = new SearchFragment();
        user = new UserFragment();

        setEvent();
    }

    private void setEvent() {
        tabView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.main_home:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame, home)
                                .commit();
                        return true;
                    case R.id.main_search:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame, search)
                                .commit();
                        return true;
                    case R.id.main_user:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame, user)
                                .commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setBar(ActionBar supportActionBar) {
        supportActionBar.setTitle("Home");
    }
}
