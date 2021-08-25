package com.example.myfirstapplication.model


class State(val name: String, var active: Boolean) {

    private var v = false

    fun updateState(){
        v = !v
    }

    fun checkState() = v

}