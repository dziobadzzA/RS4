package com.example.satapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satapp.MainActivity
import kotlinx.coroutines.launch

class SatViewModel : ViewModel() {

    private val _items = MutableLiveData<List<SatModel>>()
    val items: LiveData<List<SatModel>> get() = _items

    init {
        viewModelScope.launch {
            _items.value = SatApiImpl.getListOfSats()
        }
    }

}