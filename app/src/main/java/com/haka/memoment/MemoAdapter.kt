package com.haka.memoment

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.haka.memoment.ui.home.HomeFragment
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.memo_recycler_layout.*
import kotlinx.android.synthetic.main.memo_recycler_layout.view.*

class MemoAdapter(private val context: HomeFragment, private val memoList: RealmResults<MemoDB>) :
    RecyclerView.Adapter<MemoAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.memo_recycler_layout, parent, false)

        // TODO: 그 메모 들어가서 화면 보여줌(activity)
        // recyclerview setOnClickListener
        view.setOnClickListener {
            Toast.makeText(view.context, "recycler selected ${view.memoTextRV.text}", Toast.LENGTH_LONG).show()
            val intent = Intent(view.context, MemoDetailActivity::class.java)
            intent.putExtra("text", view.memoTextRV.text)
            intent.putExtra("date", view.textDate.text)
            // TODO: imgae, latitude, longtitude, label, id
            intent.putExtra("id", view.memoId.text)
            view.context.startActivity(intent)
        }

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
        holder.itemView.memoId.text = memoList[position]!!.id.toString()


    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        init {
            // TODO: 길게 눌렀을 때 메뉴창 바꾸기
            itemView.setOnCreateContextMenuListener(this)

        }

        val textMemo = itemView.findViewById<TextView>(R.id.memoTextRV)

        // TODO id, image, gps 추가
        val textDate = itemView.findViewById<TextView>(R.id.textDate)

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(0,R.id.ct_edit, 0,"edit")
            menu?.add(0,R.id.ct_delete, 0,"delete")
        }



    }
}

