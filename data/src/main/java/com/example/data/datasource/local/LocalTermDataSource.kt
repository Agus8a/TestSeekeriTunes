package com.example.data.datasource.local

import com.example.data.datasource.TermDataSource
import com.example.data.datasource.local.db.TestSeekeriTunesDatabase
import com.example.data.entity.TermEntity

class LocalTermDataSource(private val database: TestSeekeriTunesDatabase) : TermDataSource {
    override suspend fun saveTerm(termEntity: TermEntity) {
        val entity = database.termDao().getByText(termEntity.text)

        if (entity.id == termEntity.id) {
            database.termDao().update(termEntity)
        } else {
            database.termDao().insert(termEntity)
        }
    }

    override suspend fun getTerms(): List<TermEntity> = database.termDao().getAll()
}