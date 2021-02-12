package com.haka.memoment

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*
// TODO 만든 날짜, 수정한 날짜?
@RealmClass
open class MemoDB() : RealmObject(),Parcelable {
    @PrimaryKey
    var id: Long? = null
//    private? lateinit?

    var text: String? = null
    var date: String? = null
    var label: String? = null
//    image object   bitmap?
    var memoImage: ByteArray? = null

//    위도(latitude), 경도(longitude)
    var latitude: Double? = null
    var longitude: Double? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Long::class.java.classLoader) as? Long
        text = parcel.readString()
        date = parcel.readString()
        label = parcel.readString()
        memoImage = parcel.createByteArray()
        latitude = parcel.readValue(Double::class.java.classLoader) as? Double
        longitude = parcel.readValue(Double::class.java.classLoader) as? Double
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(text)
        parcel.writeString(date)
        parcel.writeString(label)
        parcel.writeByteArray(memoImage)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemoDB> {
        override fun createFromParcel(parcel: Parcel): MemoDB {
            return MemoDB(parcel)
        }

        override fun newArray(size: Int): Array<MemoDB?> {
            return arrayOfNulls(size)
        }
    }
}