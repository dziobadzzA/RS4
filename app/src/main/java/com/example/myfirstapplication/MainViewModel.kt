package com.example.myfirstapplication

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapplication.database.DatabaseSql
import com.example.myfirstapplication.database.MentorDatabase
import com.example.myfirstapplication.model.Mentor
import com.example.myfirstapplication.model.State
import com.example.myfirstapplication.screens.add.AddDatabaseFragment
import com.example.myfirstapplication.screens.filter.FilterPreferenceFragment
import kotlinx.coroutines.launch

class MainViewModel(database: DatabaseSql, app: Application): ViewModel() {

    var database: DatabaseSql = database
    private val databaseDAO = database.mentorDatabaseDao
    var application: Application = app
    var indexses: Long = 0

    var fragmentAddDatabase: AddDatabaseFragment
    var fragmentFilterFragment: FilterPreferenceFragment

    val addFragment: State = State("addFragment", false)
    val filterFragment: State = State("filterFragment", false)

    private var _sortype: MutableLiveData<String>? = null
    val sortype: LiveData<String>? get() = _sortype

    private var _mentors: MutableLiveData<MutableList<MentorDatabase>>? = null
    val mentors: LiveData<MutableList<MentorDatabase>>? get() = _mentors

    init {
        _sortype?.value = "name"
        _sortype = MutableLiveData<String>()
        _mentors = MutableLiveData<MutableList<MentorDatabase>>()
        fragmentAddDatabase = AddDatabaseFragment()
        fragmentFilterFragment = FilterPreferenceFragment()
        updateSortType()
        viewModelScope.launch {
            indexses = databaseDAO.getLastIndex()
        }
    }


    fun updateSortType(){

        viewModelScope.launch {

            val prefx = PreferenceManager.getDefaultSharedPreferences(application)
            _sortype?.value = prefx.getString("otbor", "name")

            viewModelScope.launch {

                when (_sortype?.value) {

                    "name" -> _mentors?.value = databaseDAO.getAllName().toMutableList()

                    "firstname" -> _mentors?.value = databaseDAO.getAllFirstName().toMutableList()

                    "phone" -> _mentors?.value = databaseDAO.getAllPhone().toMutableList()
                }
            }

        }


    }

    fun insert(mentor: Mentor) {
         viewModelScope.launch {
             val tempMentor = convertToDatabase(mentor)
             tempMentor.Id = ++indexses
             _mentors?.value?.add(tempMentor)
             databaseDAO.insert(tempMentor)
         }
    }



    fun get(key: Long) {
        viewModelScope.launch {
            databaseDAO.get(key)
        }
    }

    fun clearMentor(mentor: MentorDatabase) {

        viewModelScope.launch {
            _mentors?.value?.remove(mentor)
            databaseDAO.clearMentor(mentor.Id)
        }

    }

     suspend fun clearAll() = databaseDAO.clearAll()

     private fun convertList(inList: List<MentorDatabase>?): List<Mentor>? {
         val list: MutableList<Mentor> = mutableListOf()

         if (inList != null) {

             for(i in inList.indices){
                 val tempMentor = Mentor()
                 tempMentor.name = inList[i].name
                 tempMentor.firstname = inList[i].firstname
                 tempMentor.number = inList[i].number
                 list.add(tempMentor)
             }

         }

        return list.toList()
     }

    fun convertToDatabase(mentor: Mentor): MentorDatabase {

        val mentorDatabase = MentorDatabase()

        if (mentor.name.isNullOrEmpty())
            mentorDatabase.name == ""
        else
            mentorDatabase.name = mentor.name!!

        if (mentor.firstname.isNullOrEmpty())
            mentorDatabase.firstname == ""
        else
            mentorDatabase.firstname = mentor.firstname!!

        if (mentor.name.isNullOrEmpty())
            mentorDatabase.number == ""
        else
            mentorDatabase.number = mentor.number!!

        return mentorDatabase
    }


}