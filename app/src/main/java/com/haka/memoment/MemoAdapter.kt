package com.haka.memoment

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.haka.memoment.ui.home.HomeFragment
import io.realm.OrderedRealmCollection
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.memo_recycler_layout.*
import kotlinx.android.synthetic.main.memo_recycler_layout.view.*

// private val memoList: RealmResults<MemoDB>
class MemoAdapter(private val context: Context?, private val memoList: OrderedRealmCollection<MemoDB>) :
    RecyclerView.Adapter<MemoAdapter.Holder>() {
    var tracker: SelectionTracker<MemoDB>?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.memo_recycler_layout, parent, false)


        // TODO: 그 메모 들어가서 화면 보여줌(activity)
        // recyclerview setOnClickListener
        view.setOnClickListener {
            Toast.makeText(
                view.context,
                "recycler selected ${view.memoTextRV.text}",
                Toast.LENGTH_LONG
            ).show()
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
//        holder.itemView.memoTextRV.text = memoList[position]!!.text
//        // TODO id, image, gps 추가
//        holder.itemView.textDate.text = memoList[position]!!.date
//        holder.itemView.memoId.text = memoList[position]!!.id.toString()

        tracker?.let{
            val mMemo = memoList[position]
            holder.setMemo(mMemo)
        }

    }

    fun getItem(position: Int) = memoList[position]
    fun getPosition(id: Long) = memoList.indexOfFirst { it.id == id }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mMemo: MemoDB? = null
        var positionv: Int? = null

        init {
            // TODO: 길게 눌렀을 때 메뉴창 바꾸기(action mode)

        }

        val textMemo = itemView.findViewById<TextView>(R.id.memoTextRV)

        // TODO id, image, gps 추가
        val textDate = itemView.findViewById<TextView>(R.id.textDate)
        val textId = itemView.findViewById<TextView>(R.id.memoId)
        fun setMemo(memo: MemoDB?) {
            itemView.memoId.text = "${memo?.id}"
            itemView.memoTextRV.text = "${memo?.text}"
            itemView.textDate.text = "${memo?.date}"

            this.mMemo = mMemo
        }

        fun getItemDetails():ItemDetailsLookup.ItemDetails<MemoDB> = object :ItemDetailsLookup.ItemDetails<MemoDB>(){
            override fun getSelectionKey(): MemoDB? = memoList[position]

            override fun getPosition(): Int =adapterPosition


        }


    }
}

