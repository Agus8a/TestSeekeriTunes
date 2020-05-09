package com.example.data.datasource.local.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.entity.ResultEntity

interface ResultDao {
    @Insert
    fun insert(resultEntity: ResultEntity)

    @Update
    fun update(resultEntity: ResultEntity)

    @Delete
    fun delete(resultEntity: ResultEntity)

    @Query("SELECT * FROM ResultEntity")
    fun getAll(): List<ResultEntity>

    @Query("SELECT * FROM ResultEntity WHERE trackId = :trackId")
    fun getByTrackId(trackId: Long): ResultEntity

    @Query("SELECT * FROM ResultEntity WHERE termId = :termId")
    fun getByTeem(termId: Long): List<ResultEntity>
}