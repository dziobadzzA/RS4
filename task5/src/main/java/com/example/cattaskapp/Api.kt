package com.example.cattaskapp


import com.example.cattaskapp.data.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface CatApi {

    @GET("/v1/images/search?api-key=3437333-17ce-4a96-9ed1-716360a579b8&limit=10")
    suspend fun getListOfCats(): List<Cat>
    suspend fun updateListOfCats(): List<Cat>
}

object CatApiImpl {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.thecatapi.com")
        .build()

    private val CatService = retrofit.create(CatApi::class.java)

    suspend fun getListOfCats(): List<Cat> {
        return withContext(Dispatchers.IO) {
            CatService.getListOfCats()
        }
    }

    suspend fun updateListOfCats(): List<Cat>
    {
        return withContext(Dispatchers.IO) {
            CatService.updateListOfCats()
        }
    }
}