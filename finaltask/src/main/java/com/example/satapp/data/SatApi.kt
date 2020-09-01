package com.example.satapp.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sat(
        @Json(name = "url") var urldfr: String?,
        @Json(name = "beacon") var beacondfr: String?,
        @Json(name = "TV") var tvdfr: String?,
        @Json(name = "TTX") var ttxdfr: String?,
        @Json(name = "namesat") var namesatdfr: String?,
        @Json(name = "antensignal") var antensignaldfr: String?,
        @Json(name = "name") var namedfr: String?,
        @Json(name = "firname") var firnamedfr: String?,
        @Json(name = "point") var pointdfr: String?,
        @Json(name = "labimage") var labimagedfr: String?
)