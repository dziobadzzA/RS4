package com.example.satapp

import com.example.satapp.SatModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface SatApi {

    @GET("/.json")
    suspend fun getListOfSats(): List<SatModel>
}

object SatApiImpl {
    private val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://satapp-d1028.firebaseio.com")
            .build()

    private val SatService = retrofit.create(SatApi::class.java)

    suspend fun getListOfSats(): List<SatModel> {
        return withContext(Dispatchers.IO) {
            SatService.getListOfSats()
        }
    }

}