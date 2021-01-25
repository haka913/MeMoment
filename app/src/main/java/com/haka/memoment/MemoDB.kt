package com.haka.memoment

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*
// TODO 만든 날짜, 수정한 날짜?
@RealmClass
open class MemoDB : RealmObject() {
    @PrimaryKey
    var id: Long? = null
//    private? lateinit?

    var text: String? = null
    var date: Date? = null
    var label: String? = null
//    image object   bitmap?
    var memoImage: ByteArray? = null

//    위도(latitude), 경도(longitude)
    var latitude: Double? = null
    var longitude: Double? = null
}