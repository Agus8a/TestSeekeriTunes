package com.example.data.datasource.remote

import com.example.data.util.SEARCH_ENDPOINT
import com.example.domain.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {
    @GET(SEARCH_ENDPOINT)
    suspend fun getResults(@Query("term") term: String): List<Result>
}