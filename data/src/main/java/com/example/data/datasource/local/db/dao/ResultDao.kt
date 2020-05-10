package com.example.data.datasource.local.db.dao

import androidx.room.*
import com.example.data.entity.ResultEntity
import com.example.data.util.DEFAULT_LIMIT

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(resultEntity: ResultEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(resultEntity: ResultEntity)

    @Delete
    fun delete(resultEntity: ResultEntity)

    @Query("SELECT * FROM ResultEntity")
    fun getAll(): List<ResultEntity>

    @Query("SELECT * FROM ResultEntity WHERE trackId = :trackId")
    fun getByTrackId(trackId: Long): ResultEntity

    @Query("SELECT * FROM ResultEntity WHERE term = :term LIMIT $DEFAULT_LIMIT")
    fun getByTerm(term: String): List<ResultEntity>
}