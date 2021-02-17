package com.haka.memoment;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MemoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        String stringText = intent.getStringExtra("text");
        String stringDate = intent.getStringExtra("date");
        String stringLat = intent.getStringExtra("latitude");
        String stringLng = intent.getStringExtra("longitude");


        TextView textView1 = (TextView) findViewById(R.id.text1);
        TextView textView2 = (TextView) findViewById(R.id.text2);
        TextView txLat = (TextView) findViewById(R.id.txLat);
        TextView txLng = (TextView) findViewById(R.id.txLng);
        textView1.setText(stringText);
        textView2.setText(stringDate);
        txLat.setText(stringLat);
        txLng.setText(stringLng);

        //textView1.setText("text");
        //textView2.setText("date");



    }
}