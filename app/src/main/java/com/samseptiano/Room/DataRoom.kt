package com.samseptiano.hcroadmap.Room.EmpList

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//Entity annotation to specify the table's name
@Entity(tableName = "data")
//Parcelable annotation to make parcelable object
@Parcelize
data class DataRoom(
    //PrimaryKey annotation to declare primary key with auto increment value
    //ColumnInfo annotation to specify the column's name
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "Provinsi") var Provinsi: String = "",
    @ColumnInfo(name = "Kode_Provi") var Kode_Provi: String = "",
    @ColumnInfo(name = "Kasus_Meni") var Kasus_Meni: String = "",
    @ColumnInfo(name = "Kasus_Posi") var Kasus_Posi: String = "",
    @ColumnInfo(name = "Kasus_Semb") var Kasus_Semb: String = "",
    @ColumnInfo(name = "FID") var FID: String = ""

) : Parcelable