package com.example.foodrandom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class UploadActivity extends AppCompatActivity {

    LinearLayout addBtn;
    MaterialEditText name, link;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Toolbar toolbar = findViewById(R.id.upload_toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle("메뉴 추가하기");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name = findViewById(R.id.edit_name);
        link = findViewById(R.id.edit_link);


        addBtn = findViewById(R.id.upload_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("")||link.getText().toString().equals("")){
                    Toast.makeText(UploadActivity.this, "빈칸을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    FoodModel model = new FoodModel(name.getText().toString(), link.getText().toString());
                    databaseReference.child("list").push().setValue(model);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
