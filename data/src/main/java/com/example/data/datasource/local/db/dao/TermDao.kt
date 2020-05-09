package com.example.data.datasource.local.db.dao

import androidx.room.*
import com.example.data.entity.TermEntity

@Dao
interface TermDao {
    @Insert
    fun insert(termEntity: TermEntity)

    @Update
    fun update(termEntity: TermEntity)

    @Delete
    fun delete(termEntity: TermEntity)

    @Query("SELECT * FROM TermEntity")
    fun getAll(): List<TermEntity>

    @Query("SELECT * FROM TermEntity WHERE text = :text")
    fun getByText(text: String): TermEntity
}