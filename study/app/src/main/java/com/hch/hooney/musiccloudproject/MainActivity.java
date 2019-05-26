package com.hch.hooney.musiccloudproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hch.hooney.musiccloudproject.Do.CategoryDo;
import com.hch.hooney.musiccloudproject.Services.Binds.AudioServiceBinder;
import com.hch.hooney.musiccloudproject.Services.MusicService;
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

    private boolean isMusicPlay;

    private AudioServiceBinder binder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (AudioServiceBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Handler audioProgressHandler;

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

    @Override
    protected void onDestroy() {
        unBindMusicService();
        super.onDestroy();
    }

    private void init() {
        bindMusicService();
        isMusicPlay = false;
        nowTab = Tabs.홈;
        tabView = findViewById(R.id.main_tabs);
        panelLayout = findViewById(R.id.main_panel_layout);
        downPanelView = new DownPanelView(getApplicationContext());
        hiddenPanelView = new HiddenPanelView(getApplicationContext());
        panelLayout.addView(downPanelView);
        panelLayout.addView(hiddenPanelView);
        upPanelLayout = findViewById(R.id.main_up_panel);
        upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        home = new HomeFragment();
        search = new SearchFragment();
        user = new UserFragment();

        setEvent();
    }

    private void bindMusicService(){
        if(this.binder == null){
            Intent intent = new Intent(MainActivity.this, MusicService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    private void unBindMusicService(){
        if(this.binder != null){
            binder.destroyThread();
            unbindService(connection);
        }
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
        downPanelView.setOnPlayButtonClickListener(new DownPanelView.OnPlayButtonClickListener() {
            @Override
            public void onClicked(ImageButton button, boolean isPlay, String url) {
                Toast.makeText(MainActivity.this, "isPlay : " + isPlay, Toast.LENGTH_SHORT).show();
                isMusicPlay = isPlay;
                if(isPlay){
                    startMusic(url);
                }else{
                    binder.pauseMusic();
                }
            }
        });
        hiddenPanelView.setOnTalkButtonClickListener(new HiddenPanelView.OnTalkButtonClickListener() {
            @Override
            public void onClick(ImageButton talkBtn, SeekBar seekBar, CategoryDo item) {
                Intent intent = new Intent(getApplicationContext(), TalkActivity.class);
                intent.putExtra("progress", convertDurationToText(seekBar.getProgress()));
                intent.putExtra("m_id", item.getC_id());
                startActivity(intent);
            }
        });
        hiddenPanelView.setOnSeekbarClickListener(new HiddenPanelView.OnSeekbarClickListener() {
            @Override
            public void progress(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    binder.setDuration(progress);
                }
            }
        });
        upPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                hiddenPanelView.setAlpha(slideOffset);
                downPanelView.setAlpha(1-slideOffset);
                tabView.setAlpha(1-slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    tabView.setVisibility(View.GONE);
                }else if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    tabView.setVisibility(View.VISIBLE);
                }
            }
        });
        home.setOnItenClickListner(new HomeFragment.OnItemClickListener() {
            @Override
            public void onItemClick(View view, CategoryDo item) {
                Toast.makeText(MainActivity.this, "item : " + item.getC_title(), Toast.LENGTH_SHORT).show();
                downPanelView.setViewData(item.getC_title(), item.getC_singer(), item.getC_url());
                hiddenPanelView.setMusic(item);
                hiddenPanelView.setViewData(item.getC_title(), item.getC_singer(), item.getC_image_url());
                upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

                if(isMusicPlay){
                    startMusic(item.getC_url());
                }
            }
        });
    }

    private String convertDurationToText(int progress) {
        int min = (progress / 60000) % 60000;
        int sec = (progress % 60000) / 1000;
        String musicTime = String.format("%02d:%02d", min, sec);
        return musicTime;
    }

    private void startMusic(String url) {
        createProgressHandler();
        binder.setProgressUpdateHandler(audioProgressHandler);
        binder.setMediaPlayer(getApplicationContext(), url);
    }

    private void createProgressHandler() {
        if(audioProgressHandler == null){
            audioProgressHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what){
                        case AudioServiceBinder.UPDATE_AUDIO_PROGRESS:
                            if(binder != null){
                                int currentProgress = binder.getProgress();
                                int maxDuration = binder.getDuration();
                                hiddenPanelView.setMaxDuration(maxDuration);
                                hiddenPanelView.setSeekProgress(currentProgress);
                            }
                            return true;
                        case AudioServiceBinder.PLAY_AUDIO:
                            if((boolean)msg.obj){
                                downPanelView.getPlayBtn().setImageDrawable(getResources().getDrawable(R.drawable.ic_pause, null));
                            }else{
                                downPanelView.getPlayBtn().setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow, null));
                            }
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
    }
}
