package com.haka.memoment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.memo_recycler_layout.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class AddMemoActivity : AppCompatActivity() {

    private lateinit var memoText:EditText
    private lateinit var btnSaveMemo:Button
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memo)

        // init view
        realm = Realm.getDefaultInstance()
        memoText = findViewById(R.id.memoText)
        btnSaveMemo = findViewById(R.id.btnSaveMemo)

        // setonclicker
        btnSaveMemo.setOnClickListener {
            addMemoDB()
        }
    }

    private fun addMemoDB() {
        try {
            // increase id
            realm.beginTransaction()
            val curIdNum:Number? = realm.where(MemoDB::class.java).max("id")
            val nxtId:Long

            nxtId = if(curIdNum==null){
                1
            }else{
                curIdNum.toLong()+1
            }

            val memo = MemoDB()
            memo.id = nxtId
            memo.text = memoText.text.toString()
            // TODO "나머지 attr 추가하기"
            // date 추가
            val now = Calendar.getInstance().time
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            memo.date = sdf.format(now).toString()

            // copy this transaction & commit
            realm.copyToRealmOrUpdate(memo)
            realm.commitTransaction()

            Toast.makeText(this, "메모가 저장되었습니다", Toast.LENGTH_LONG).show()

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }catch (e: Exception){
            Toast.makeText(this, "Failed $e", Toast.LENGTH_SHORT).show()
        }

    }
}