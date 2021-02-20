package com.haka.memoment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MemoDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_detail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        String stringText = intent.getStringExtra("text");
        String stringDate = intent.getStringExtra("date");
        String stringLat = intent.getStringExtra("latitude");
        String stringLng = intent.getStringExtra("longitude");
        final Geocoder geocoder = new Geocoder(this);
        List<Address> addr = null;
        double latitude = Double.parseDouble(stringLat);
        double longitude = Double.parseDouble(stringLng);
        try {
            addr = geocoder.getFromLocation(latitude, longitude, 10);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("addr error", "$e");
        }


        TextView textView1 = (TextView) findViewById(R.id.memo_text);
        TextView textView2 = (TextView) findViewById(R.id.date_text);
        TextView txLat = (TextView) findViewById(R.id.txLat);
        TextView txLng = (TextView) findViewById(R.id.txLng);
        TextView txAddr = (TextView) findViewById(R.id.txAddr);

        textView1.setText(stringText);
        textView2.setText(stringDate);
        txLat.setText(stringLat);
        txLng.setText(stringLng);
        String geoToAddr = "";
        if (addr != null) {
            if (addr.size() == 0) {
                txAddr.setText("해당 주소 정보가 없습니다.");
            }else{
                geoToAddr = addr.get(0).getAddressLine(0).toString();
//                txAddr.setText(addr.get(0).toString());
                txAddr.setText(geoToAddr);
            }
        }



        //edit


        //delete button




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(0,R.anim.sliding_down);

    }


}