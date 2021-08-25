package com.example.myfirstapplication


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapplication.database.DatabaseSql


class MainViewModelFactory(
    private val dataSource: DatabaseSql,
    private val app: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataSource, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
