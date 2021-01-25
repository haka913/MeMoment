package com.haka.memoment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.RealmResults

// TODO 정렬을 시간 최근 순으로 정렬하기
// TODO 메모 수정, 삭제 구현
// TODO 메모 searchView로 찾는 기능 구현
class MainActivity : AppCompatActivity() {

    private lateinit var addMemo: FloatingActionButton
    private lateinit var memoRecyclerview: RecyclerView
    private lateinit var memoList: ArrayList<MemoDB>
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init view
        realm = Realm.getDefaultInstance()
        addMemo = findViewById(R.id.fbtnAddMemo)
        memoRecyclerview = findViewById(R.id.memoRecycler)

        // setOnclick

        addMemo.setOnClickListener {

            startActivity(Intent(this, AddMemoActivity::class.java ))
            finish()

        }
        memoRecyclerview.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

        getAllMemo()
    }

    private fun getAllMemo() {
        memoList = ArrayList()

        val results: RealmResults<MemoDB> = realm.where<MemoDB>(MemoDB::class.java).findAll()

        memoRecyclerview.adapter = MemoAdapter(this, results)
        memoRecyclerview.adapter!!.notifyDataSetChanged()

    }
}