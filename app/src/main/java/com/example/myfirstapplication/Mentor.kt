package com.example.myfirstapplication

class Mentor
{
    private var name: String? = null
    private var firname: String? = null
    private var number: String? = null

    fun Mentor(name: String?, firname: String?, number: String?) {
        this.name = name
        this.firname = firname
        this.number = number
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getFirname(): String? {
        return firname
    }

    fun setFirname(firname: String?) {
        this.firname = firname
    }

    fun getNumber(): String? {
        return number
    }

    fun setNumber(number: String?) {
        this.number = number
    }

}