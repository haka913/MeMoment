package com.haka.memoment.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.ActionMode
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.haka.memoment.*
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.memo_recycler_layout.*

class HomeFragment : Fragment(), ActionMode.Callback {
    //TODO: fragment에 addMemoBtn 추가하기
    private lateinit var memoRecyclerview: RecyclerView

//    private var realm = Realm.getDefaultInstance()
    private lateinit var realm: Realm
    private lateinit var adapter: MemoAdapter

    private var selectedMemoList: MutableList<MemoDB> = mutableListOf()
    private var tracker: SelectionTracker<MemoDB>? = null
    private var actionMode: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        realm = Realm.getDefaultInstance()
        val realmResult:RealmResults<MemoDB> = realm.where<MemoDB>().findAll()
        memoRecyclerview = view.findViewById(R.id.memoRecycler)

//        val results: RealmResults<MemoDB> = realm.where<MemoDB>(MemoDB::class.java).findAll()
        //sort("date", Sort.DESCENDING)


        adapter = MemoAdapter(this.context)
        realmResult.addChangeListener { _->
            adapter.notifyDataSetChanged()
        }
        memoRecyclerview.adapter = adapter

        adapter.memoList.addAll(realmResult)

        memoRecyclerview.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        tracker = SelectionTracker.Builder<MemoDB>(
            "memoSelection",
            memoRecyclerview,
            MemoKeyProvider(adapter),
            MemoDetailLookup(memoRecyclerview),
            StorageStrategy.createParcelableStorage(MemoDB::class.java)
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<MemoDB>(){
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    tracker?.let{
                        selectedMemoList = it.selection.toMutableList()
                        // TODO: DO WHATEVER YOU NEED WITH THE SELECTED ITEMS HERE
                        if(selectedMemoList.isEmpty()){
                            actionMode?.finish()
                        }else{
                            if(actionMode==null){
//                                actionMode = this@HomeFragment.activity?.startActionMode(this@HomeFragment)
                                actionMode = activity?.startActionMode(this@HomeFragment)
                            }
                            actionMode?.title="${selectedMemoList.size}"
                        }
                    }
                }
            }
        )

        adapter.tracker = tracker

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun deleteMemo(selectmemoList: MutableList<MemoDB>) {
        try {
            realm.beginTransaction()
            for(memo in selectmemoList){
                Log.d("realm delete", "${memo.id}, ${memo.latitude}, ${memo.longitude}")
                // adapter list를 먼저 지우고 알린후 realm DB 접근해서 지운다
                adapter.memoList.remove(memo)
                adapter.notifyDataSetChanged()
                val deleteMemo = realm.where<MemoDB>().equalTo("id", memo.id).findFirst()!!
                deleteMemo?.deleteFromRealm()
            }
            realm.commitTransaction()


        } catch (e: Exception) {
            Toast.makeText(context, "Failed $e", Toast.LENGTH_SHORT).show()
            Log.d("realm error", "$e")
        }

    }


    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.AMdeleteMenu->{
                var builder = AlertDialog.Builder(this.context)
                builder.setTitle("Warning")
                builder.setMessage("정말로 지우시겠습니까?")

                var listner = object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        when(which){
                            DialogInterface.BUTTON_POSITIVE->{
                                deleteMemo(selectedMemoList)
                            }
                            DialogInterface.BUTTON_NEGATIVE->{

                            }
                        }
                    }
                }
                builder.setPositiveButton("삭제", listner)
                builder.setNegativeButton("취소", listner)
                builder.show()
            }
            R.id.AMcopyMemo->{
                Toast.makeText(this.context, "selected copy memo", Toast.LENGTH_LONG).show()
            }
            R.id.AMgotoCalendar->{
                Toast.makeText(this.context, "selected Calendar memo", Toast.LENGTH_LONG).show()
            }
            R.id.AMpinMemo->{
                Toast.makeText(this.context, "selected pin memo", Toast.LENGTH_LONG).show()
            }
        }
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.let{
            val inflater: MenuInflater = it.menuInflater
            inflater.inflate(R.menu.main_action_mode, menu)
            return true
        }
        return false
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        adapter.tracker?.clearSelection()
        adapter.notifyDataSetChanged()
        actionMode = null
    }
}