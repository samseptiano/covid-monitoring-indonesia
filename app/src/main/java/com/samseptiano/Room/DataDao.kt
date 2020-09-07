package com.samseptiano.hcroadmap.Room.EmpList

import androidx.room.*

@Dao
interface DataDao {

    @Insert
    fun insert(dataRoom : DataRoom)

    @Update
    fun update(dataRoom: DataRoom)

    @Delete
    fun delete(dataRoom: DataRoom)

    @Query("SELECT * FROM data ")
    fun getAll() : List<DataRoom>

    @Query("DELETE FROM data ")
    fun deleteAll() : Unit

    @Query("SELECT * FROM data WHERE id = :id")
    fun getById(id: Int) : List<DataRoom>
}