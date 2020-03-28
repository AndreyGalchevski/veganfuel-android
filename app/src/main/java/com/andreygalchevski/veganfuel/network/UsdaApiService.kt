package com.andreygalchevski.veganfuel.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nal.usda.gov/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface UsdaApiService {
    @GET("ndb/nutrients?format=json&max=1500&api_key=QQ4HZzADiGZBvTyk5606Fb1axjFItcxuhYfct882")
    suspend fun getFoods(
        @Query("sort") sortTypeId: String,
        @Query("fg") foodGroupIds: List<String>,
        @Query("nutrients") nutrientId: String
    ): UsdaResponse
}

object UsdaApi {
    val retrofitService: UsdaApiService by lazy {
        retrofit.create(UsdaApiService::class.java)
    }
}