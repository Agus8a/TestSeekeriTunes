package com.example.data.datasource

import com.example.data.entity.TermEntity

interface TermDataSource {
    suspend fun saveTerm(termEntity: TermEntity)
    suspend fun getTerms(): List<TermEntity>
}