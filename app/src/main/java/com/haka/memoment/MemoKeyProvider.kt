package com.haka.memoment

import androidx.recyclerview.selection.ItemKeyProvider

class MemoKeyProvider(private val adapter: MemoAdapter):ItemKeyProvider<MemoDB>(SCOPE_CACHED) {
    override fun getKey(position: Int): MemoDB? {
        return adapter.getItem(position)
    }

    override fun getPosition(key: MemoDB): Int {
        return adapter.getPosition(key.id!!)
    }

}