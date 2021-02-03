package com.haka.memoment.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.haka.memoment.MemoAdapter
import com.haka.memoment.MemoDB
import com.haka.memoment.MemoDetailActivity
import com.haka.memoment.R
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.memo_recycler_layout.*

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
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        realm = Realm.getDefaultInstance()
        memoRecyclerview = view.findViewById(R.id.memoRecycler)
        getAllMemo()
        //get all memo
        memoList = ArrayList()

        val results: RealmResults<MemoDB> = realm.where<MemoDB>(MemoDB::class.java).findAll()
        //sort("date", Sort.DESCENDING)
        memoList.addAll(results)
        memoRecyclerview.adapter = MemoAdapter(this.context, results)
        memoRecyclerview.adapter!!.notifyDataSetChanged()

        registerForContextMenu(memoRecyclerview)
        memoRecyclerview.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
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

                val deleteID = this.memoId.text.toString()
                deleteMemo(deleteID)
                Toast.makeText(context, "deleteId is ${deleteID}", Toast.LENGTH_LONG).show()

            }
        }
        return super.onContextItemSelected(item)
    }

    private fun deleteMemo(id: String?){
        try {
            realm.beginTransaction()
            val deleteItem = realm.where<MemoDB>().equalTo("id",id?.toLong()).findFirst()!!


            deleteItem.deleteFromRealm()

            realm.commitTransaction()

            memoRecyclerview.adapter!!.notifyDataSetChanged()

        }catch (e:Exception){
            Toast.makeText(context, "Failed $e", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getAllMemo() {
        memoList = ArrayList()

        val results: RealmResults<MemoDB> = realm.where<MemoDB>(MemoDB::class.java).findAll()
        //sort("date", Sort.DESCENDING)
//        memoList.addAll(results)
        memoRecyclerview.adapter = MemoAdapter(this.context, results)
        memoRecyclerview.adapter!!.notifyDataSetChanged()

    }
}