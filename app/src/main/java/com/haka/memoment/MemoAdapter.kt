package com.haka.memoment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import kotlinx.android.synthetic.main.memo_recycler_layout.view.*

class MemoAdapter(private val context:Context?, private val memoList: RealmResults<MemoDB>):RecyclerView.Adapter<MemoAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.memo_recycler_layout, parent, false)

//        return RecyclerView.ViewHolder(view)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return memoList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.memoTextRV.text = memoList[position]!!.text
        // TODO id, image, gps 추가
        holder.itemView.textDate.text = memoList[position]!!.date
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textMemo = itemView.findViewById<TextView>(R.id.memoTextRV)
        // TODO id, image, gps 추가
        val textDate = itemView.findViewById<TextView>(R.id.textDate)
    }
}

