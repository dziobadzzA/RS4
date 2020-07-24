package com.example.cattaskapp.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cat(
    @Json(name = "id") var iddfr: String?,
    @Json(name = "url") var urldfr: String?
)

