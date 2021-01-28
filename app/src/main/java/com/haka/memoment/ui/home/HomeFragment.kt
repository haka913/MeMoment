package com.haka.memoment.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.haka.memoment.AddMemoActivity
import com.haka.memoment.MemoAdapter
import com.haka.memoment.MemoDB
import com.haka.memoment.R
import io.realm.Realm
import io.realm.RealmResults

class HomeFragment : Fragment() {
//TODO: fragment에 addMemoBtn 추가하기
    private lateinit var memoRecyclerview: RecyclerView
    private lateinit var memoList: ArrayList<MemoDB>
    private lateinit var realm: Realm

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)



        realm = Realm.getDefaultInstance()
        memoRecyclerview = root.findViewById(R.id.memoRecycler)

        memoRecyclerview.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        getAllMemo()
        return root
    }

    private fun getAllMemo() {
        memoList = ArrayList()

        val results: RealmResults<MemoDB> = realm.where<MemoDB>(MemoDB::class.java).findAll()

        memoRecyclerview.adapter = MemoAdapter(this, results)
        memoRecyclerview.adapter!!.notifyDataSetChanged()

    }
}