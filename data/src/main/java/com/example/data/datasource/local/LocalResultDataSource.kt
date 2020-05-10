package com.example.data.datasource.local

import com.example.data.datasource.ResultDataSource
import com.example.data.datasource.local.db.TestSeekeriTunesDatabase
import com.example.data.entity.ResultEntity

class LocalResultDataSource(private val database: TestSeekeriTunesDatabase) : ResultDataSource {
    override suspend fun saveResult(resultEntity: ResultEntity) {
        database.resultDao().insert(resultEntity)
    }

    override suspend fun getResultsByTerm(idTerm: Long): List<ResultEntity> =
        database.resultDao().getByTerm(idTerm)
}