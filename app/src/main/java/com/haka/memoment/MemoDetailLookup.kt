package com.haka.memoment

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView

class MemoDetailLookup(private val recyclerView:RecyclerView):ItemDetailsLookup<MemoDB>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<MemoDB>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if(view!=null){
            return (recyclerView.getChildViewHolder(view) as MemoAdapter.Holder).getItemDetails()
        }
        return null
    }

}