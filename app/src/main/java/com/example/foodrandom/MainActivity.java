package com.example.foodrandom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gelitenight.waveview.library.WaveView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    LinearLayout pickBtn, uploadBtn;

    ImageView image;

    RelativeLayout waveFrame;
    private WaveHelper mWaveHelper;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    List<FoodModel> items = new ArrayList<>();

    private int imageClick=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waveFrame = findViewById(R.id.waveFrame);

        final WaveView waveView = findViewById(R.id.wave);
        mWaveHelper = new WaveHelper(waveView);

        waveView.setShapeType(WaveView.ShapeType.SQUARE);
        waveView.setWaveColor(
                //물결 색상 설정
                Color.parseColor("#284fc3f7"),
                Color.parseColor("#3c4fc3f7"));

        databaseReference.child("list").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                items.add(dataSnapshot.getValue(FoodModel.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pickBtn = findViewById(R.id.pick_btn);
        pickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(items.size()==0){
                    Toast.makeText(MainActivity.this, "인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    waveFrame.setVisibility(View.VISIBLE);
                    mWaveHelper.start();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mWaveHelper.cancel();
                            waveFrame.setVisibility(View.GONE);
                            Intent intent = new Intent(MainActivity.this, FoodActivity.class);
                            int rand = new Random().nextInt(items.size());
                            intent.putExtra("name", items.get(rand).getName());
                            intent.putExtra("link", items.get(rand).getLink());
                            startActivity(intent);
                        }
                    }, 5000);

                }
            }
        });

        uploadBtn = findViewById(R.id.upload_btn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UploadActivity.class));
            }
        });

        image = findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageClick++;
                if(imageClick>4) image.setImageResource(R.drawable.bigdata2);
            }
        });


    }
}
