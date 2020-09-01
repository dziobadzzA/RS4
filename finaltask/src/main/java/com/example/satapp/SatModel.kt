package com.example.satapp

import com.squareup.moshi.Json

class SatModel
{
    private var name: String? = null
    private var firname: String? = null
    private var labimage: String? = null
    private var url:String? = null
    private var beacon: String? = null
    private var TV: String? = null
    private var TTX: String? = null
    private var namesat: String? = null
    private var antensignal: String? = null
    private var point: String? = null

    fun SatModel(name: String?, firname: String?, labimage:String?, url:String?,
                 beacon: String?, TV: String?, TTX: String?, namesat:String?, antensignal:String?,
                 point: String?
    ) {
        this.name = name
        this.firname = firname
        this.labimage = labimage
        this.url = url
        this.beacon = beacon
        this.TV = TV
        this.TTX = TTX
        this.namesat = namesat
        this.antensignal = antensignal
        this.point = point
    }


    fun getPoint(): String? { return point }

    fun setPoint(point: String?) { this.point = point }

    fun getAntensignal(): String? { return antensignal }

    fun setAntensignal(antensignal: String?) { this.antensignal = antensignal }

    fun getNamesat(): String? { return namesat }

    fun setNamesat(namesat: String?) { this.namesat = namesat }

    fun getTTX(): String? { return TTX }

    fun setTTX(TTX: String?) { this.TTX = TTX }

    fun getTV(): String? { return TV }

    fun setTV(TV: String?) { this.TV= TV }

    fun getBeacon(): String? { return beacon }

    fun setBeacon(beacon: String?) { this.beacon = beacon }

    fun getUrl(): String? { return url }

    fun setUrl(url: String?) { this.url = url }

    fun getName(): String? { return name }

    fun setName(name: String?) { this.name = name }

    fun getFirname(): String? { return firname }

    fun setFirname(firname: String?) { this.firname = firname }

    fun getImage(): String? { return this.labimage }

    fun setImage(labimage: String) { this.labimage = labimage }

}