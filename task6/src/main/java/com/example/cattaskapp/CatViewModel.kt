package com.example.cattaskapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cattaskapp.Channel
import kotlinx.coroutines.launch
import com.example.cattaskapp.MainActivity

class CatViewModel : ViewModel() {

    private val _items = MutableLiveData<List<Channel>>()
    val items: LiveData<List<Channel>> get() = _items

    init {
        viewModelScope.launch {
            _items.value = MainActivity.CatApiImpl.getListOfCats()
        }
    }

}