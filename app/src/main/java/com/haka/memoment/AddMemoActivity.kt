package com.haka.memoment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import io.realm.Realm
import kotlinx.android.synthetic.main.memo_recycler_layout.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class AddMemoActivity : AppCompatActivity() {

    private lateinit var memoText: EditText
    private lateinit var btnSaveMemo: Button
    private lateinit var realm: Realm

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude:Double? = null
    private var longitude:Double? = null
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memo)

        // init view
        realm = Realm.getDefaultInstance()
        memoText = findViewById(R.id.memoText)
        btnSaveMemo = findViewById(R.id.btnSaveMemo)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        updateLocation()
        // setonclicker
        btnSaveMemo.setOnClickListener {
            addMemoDB()
        }
    }

    @SuppressLint("MissingPermission")
    fun updateLocation(){
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            // 1000 (1 sec) *60 -> 1min
            interval = 1000*60
        }
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let{
                    for((i,location) in it.locations.withIndex()){
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

//    @SuppressLint("MissingPermission")
//    private fun getLtgLng(){
//        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location:Location? ->
//            latitude = location?.latitude
//            longitude = location?.longitude
//            Log.d("location", "$latitude , $longitude")
//        }
//    }
    private fun addMemoDB() {
        try {

            // increase id
            realm.beginTransaction()
            val curIdNum: Number? = realm.where(MemoDB::class.java).max("id")
            val nxtId: Long

            nxtId = if (curIdNum == null) {
                1
            } else {
                curIdNum.toLong() + 1
            }

            val memo = MemoDB()
            memo.id = nxtId
            memo.text = memoText.text.toString()
            // TODO "나머지 attr 추가하기"
            // date 추가
            val now = Calendar.getInstance().time
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            memo.date = sdf.format(now).toString()

            memo.latitude = latitude
            memo.longitude = longitude

            // copy this transaction & commit
            realm.copyToRealmOrUpdate(memo)
            realm.commitTransaction()

            Toast.makeText(this, "메모가 저장되었습니다", Toast.LENGTH_LONG).show()

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } catch (e: Exception) {
            Toast.makeText(this, "Failed $e", Toast.LENGTH_SHORT).show()
        }

    }
}