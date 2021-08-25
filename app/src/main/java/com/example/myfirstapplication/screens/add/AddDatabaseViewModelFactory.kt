package com.example.myfirstapplication.screens.add


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddDatabaseViewModelFactory : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddDatabaseFragmentViewModel::class.java)) {
            return AddDatabaseFragmentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}