package com.example.myfirstapplication.screens.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddDatabaseFragmentViewModel: ViewModel() {

    private var _name: MutableLiveData<String>? = null
    var name: LiveData<String>? = _name

    private var _firstname: MutableLiveData<String>? = null
    var firstname: LiveData<String>? = _firstname

    private var _number: MutableLiveData<String>? = null
    var number: LiveData<String>? = _number

    init {
        _name = MutableLiveData<String>()
        _firstname = MutableLiveData<String>()
        _number = MutableLiveData<String>()

        _name!!.value = ""
        _firstname!!.value = ""
        _number!!.value = ""
    }

}