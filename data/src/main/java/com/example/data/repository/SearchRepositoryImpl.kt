package com.example.data.repository

import com.example.data.datasource.ResultDataSource
import com.example.data.datasource.SearchDataSource
import com.example.data.mapper.ResultEntityToModel
import com.example.data.mapper.ResultModelToEntity
import com.example.domain.model.Result
import com.example.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val searchDataSource: SearchDataSource,
    private val resultDataSource: ResultDataSource,
    private val resultEntityToModel: ResultEntityToModel,
    private val resultModelToEntity: ResultModelToEntity
) : SearchRepository {
    override suspend fun searchByTerm(term: String): List<Result> {
        var results = searchDataSource.searchByTerm(term)

        if (results.isNotEmpty()) {
            val entities = resultModelToEntity.mapAll(results)
            entities.forEach { resultDataSource.saveResult(it) }
        } else {
            val entities = resultDataSource.getResultsByTerm(term)
            results = resultEntityToModel.mapAll(entities)
        }

        return results
    }
}