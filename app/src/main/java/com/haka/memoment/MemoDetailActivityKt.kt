package com.haka.memoment

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_memo_detail_kt.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.IOException

class MemoDetailActivityKt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_detail_kt)

        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = getIntent()
        val stringText = intent.getStringExtra("text")
        val stringDate = intent.getStringExtra("date")
        val stringLat = intent.getStringExtra("latitude")
        val stringLng = intent.getStringExtra("longitude")
        val geocoder = Geocoder(this)
        var addr:List<Address>? = null
        val latitude = java.lang.Double.parseDouble(stringLat)
        val longitude = java.lang.Double.parseDouble(stringLng)
        try
        {
            addr = geocoder.getFromLocation(latitude, longitude, 10)
        }
        catch (e: IOException) {
            e.printStackTrace()
            Log.d("addr error", "\$e")
        }
        val textView1 = findViewById<TextView>(R.id.text1)
        val textView2 = findViewById<TextView>(R.id.text2)
        val txLat = findViewById<TextView>(R.id.txLat)
        val txLng = findViewById<TextView>(R.id.txLng)
        val txAddr = findViewById<TextView>(R.id.txAddr)
        textView1.setText(stringText)
        textView2.setText(stringDate)
        txLat.setText(stringLat)
        txLng.setText(stringLng)
        var geoToAddr = ""
        if (addr != null)
        {
            if (addr.size === 0)
            {
                txAddr.setText("해당 주소 정보가 없습니다.")
            }
            else
            {
                geoToAddr = addr.get(0).getAddressLine(0).toString()
                // txAddr.setText(addr.get(0).toString());
                txAddr.setText(geoToAddr)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu);
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item){
//            R.id.
//        }
//        return super.onOptionsItemSelected(item)
//    }

    override fun onBackPressed() {
//        super.onBackPressed()
        finish()
        overridePendingTransition(0,R.anim.sliding_down)
    }
}