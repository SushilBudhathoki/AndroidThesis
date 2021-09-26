package com.xrest.finalassignment.Class

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Food(

        var Name:String?=null,
        var Description:String?=null,
        var Rating:String?=null,
        var Price:String?=null,
        var Image:String?=null,
        var time:String?=null,
        @PrimaryKey
        val _id: String =""




):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Name)
        parcel.writeString(Description)
        parcel.writeString(Rating)
        parcel.writeString(Price)
        parcel.writeString(Image)
        parcel.writeString(time)
        parcel.writeString(_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Food> {
        override fun createFromParcel(parcel: Parcel): Food {
            return Food(parcel)
        }

        override fun newArray(size: Int): Array<Food?> {
            return arrayOfNulls(size)
        }
    }
}