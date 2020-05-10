package com.example.data.datasource.local

import com.example.data.datasource.TermDataSource
import com.example.data.datasource.local.db.TestSeekeriTunesDatabase
import com.example.data.entity.TermEntity

class LocalTermDataSource(private val database: TestSeekeriTunesDatabase) : TermDataSource {
    override suspend fun saveTerm(term: String): TermEntity {
        val entity = database.termDao().getByText(term)

        return if (entity.id == 0L) {
            entity.text = term
            database.termDao().insert(entity)
            database.termDao().getByText(term)
        } else {
            entity
        }
    }

    override suspend fun getTerms(): List<TermEntity> = database.termDao().getAll()
}