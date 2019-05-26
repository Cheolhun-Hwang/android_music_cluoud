package com.hch.hooney.musiccloudproject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hch.hooney.musiccloudproject.Do.CategoryDo;
import com.hch.hooney.musiccloudproject.Do.TalkData;
import com.hch.hooney.musiccloudproject.listPack.TalkListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TalkActivity extends AppCompatActivity {
    private final String TAG = TalkActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private EditText editText;
    private Button sendBtn;
    private TextView progressText;

    private TalkListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        init();
        loadData();
    }

    private void loadData() {
        if(getIntent().getStringExtra("m_id") != null){
            MainActivity.rootRef
                    .child("Talk")
                    .child(getIntent().getStringExtra("m_id"))
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<TalkData> tempList = new ArrayList<>();
                            for(DataSnapshot node : dataSnapshot.getChildren()){
                                TalkData item = node.getValue(TalkData.class);
                                tempList.add(item);
                            }
                            adapter.setList(tempList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }
    }

    private void init() {
        adapter = new TalkListAdapter();
        progressText = findViewById(R.id.talk_progress_text);
        if(getIntent().getStringExtra("progress") != null){
            progressText.setText(getIntent().getStringExtra("progress"));
        }

        recyclerView = findViewById(R.id.talk_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                getApplicationContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
        editText = findViewById(R.id.talk_edit);
        sendBtn = findViewById(R.id.talk_send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().trim().isEmpty()){
                    TalkData item = new TalkData();
                    item.setUserName("Root");
                    item.setUserId("0001");
                    item.setMsg(editText.getText().toString());
                    item.setCreateDate(getNowDateTime());


                    MainActivity.rootRef
                            .child("Talk")
                            .child(getIntent().getStringExtra("m_id"))
                            .child(makeKey())
                            .setValue(item)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    editText.setText("");
                                    Toast.makeText(TalkActivity.this, "Send Complete...", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    private String getNowDateTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA);
        return simple.format(date);
    }

    private String makeKey(){
        SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd_hhmmsss", Locale.KOREA);
        return simple.format(new Date(System.currentTimeMillis()));
    }


}
