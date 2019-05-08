package com.hch.hooney.musiccloudproject;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hch.hooney.musiccloudproject.fragments.HomeFragment;
import com.hch.hooney.musiccloudproject.fragments.SearchFragment;
import com.hch.hooney.musiccloudproject.fragments.UserFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference rootRef = database.getReference("MusicCloud");

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

        home = new HomeFragment();
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
