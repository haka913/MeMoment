package com.haka.memoment.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.haka.memoment.*
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.memo_recycler_layout.*
import kotlinx.android.synthetic.main.memo_recycler_layout.view.*

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
        registerForContextMenu(memoRecyclerview)


        memoRecyclerview.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)


        getAllMemo()

        // TODO: 길게 눌렀을 때 메뉴창 바꾸기
//        memoRecyclerview.setOnLongClickListener {
//
//        }
        return root
    }

    // TODO: contextmenu clickListener (realm delete 수행하기)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.ct_edit->{
                Toast.makeText(context, "recycler selected by context Menu ${memoTextRV.text}", Toast.LENGTH_LONG).show()
                val intent = Intent(context, MemoDetailActivity::class.java)
                intent.putExtra("text", memoTextRV.text)
                intent.putExtra("date", textDate.text)
                intent.putExtra("id", memoId.text)
                // TODO: imgae, latitude, longtitude, label
                context?.startActivity(intent)

            }
            R.id.ct_delete->{

                Toast.makeText(context, "recycler selected by context Menu deleted", Toast.LENGTH_LONG).show()
                // delete memo DB from realm
                deleteMemo(memoId.text.toString().toLong())

                // 추가해야되나?
//                getAllMemo()

            }
        }
        return super.onContextItemSelected(item)
    }

    private fun deleteMemo(id: Long){
        realm.beginTransaction()
        val deleteItem = realm.where<MemoDB>(MemoDB::class.java).equalTo("id", id).findFirst()!!

        deleteItem.deleteFromRealm()
        realm.commitTransaction()

        memoRecyclerview.adapter!!.notifyDataSetChanged()

        Toast.makeText(context, "deleted memo", Toast.LENGTH_LONG).show()

    }

    private fun getAllMemo() {
        memoList = ArrayList()

        val results: RealmResults<MemoDB> = realm.where<MemoDB>(MemoDB::class.java).findAll().sort("date", Sort.DESCENDING)

        memoRecyclerview.adapter = MemoAdapter(this, results)
        memoRecyclerview.adapter!!.notifyDataSetChanged()

    }
}