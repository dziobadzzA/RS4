package com.example.myfirstapplication

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
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

    var typeDatabase: String = ""

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

            val pref = PreferenceManager.getDefaultSharedPreferences(application)
            _sortype?.value = pref.getString("otbor", "name")

            typeDatabase = pref.getString("db", "room").toString()

            viewModelScope.launch {

                if (typeDatabase == "room") {
                    when (_sortype?.value) {

                        "name" -> _mentors?.value = databaseDAO.getAllName().toMutableList()

                        "firstname" -> _mentors?.value = databaseDAO.getAllFirstName().toMutableList()

                        "phone" -> _mentors?.value = databaseDAO.getAllPhone().toMutableList()
                    }
                }
                else {
                    _mentors?.value =  _sortype?.value?.let { getSortType(it) }
                }

            }

        }


    }

    fun insert(mentor: Mentor) {
         viewModelScope.launch {
             val tempMentor = convertToDatabase(mentor)
             tempMentor.Id = ++indexses
             _mentors?.value?.add(tempMentor)

             if (typeDatabase == "room"){
                 databaseDAO.insert(tempMentor)
             }
             else{
                addCursorItem(tempMentor)
             }


         }
    }

    fun get(key: Long) {
        viewModelScope.launch {
            databaseDAO.get(key)
        }
    }


    fun updateMentor(mentor: Mentor){

        val id = getUnique(mentor)

        viewModelScope.launch {

                if (id != null) {
                    id.number = mentor.number.toString()

                    if (typeDatabase == "room"){
                        databaseDAO.update(mentor.name!!, mentor.firstname!!, mentor.number!!, id.Id)
                    }
                    else{
                        updateCursorItem(convertToDatabase(mentor), id.Id)
                    }


                }

            }

    }

    fun clearMentor(mentor: MentorDatabase) {

        viewModelScope.launch {
            _mentors?.value?.remove(mentor)

            if (typeDatabase == "room") {
                databaseDAO.clearMentor(mentor.Id)
            }
            else {
                clearCursorItem(mentor.Id)
            }

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

     private fun getSortType(typeSort:String): MutableList<MentorDatabase> {

         val db: SQLiteDatabase = application.applicationContext.openOrCreateDatabase("Mentor", Context.MODE_PRIVATE,  null)
         val query: Cursor = db.rawQuery("SELECT * FROM Mentor ORDER BY $typeSort;", null)

         val result: MutableList<MentorDatabase> = mutableListOf()

         if (query.moveToFirst()) {

             do {

                 val selectMentorDatabase = MentorDatabase()
                 val id: Long = query.getLong(0)
                 val name: String? = query.getString(1)
                 val firstname: String? = query.getString(2)
                 val number: String? = query.getString(3)

                 selectMentorDatabase.Id = id
                 selectMentorDatabase.name = name.orEmpty()
                 selectMentorDatabase.firstname = firstname.orEmpty()
                 selectMentorDatabase.number = number.orEmpty()
                 result.add(selectMentorDatabase)


             } while (query.moveToNext())
         }

         query.close()
         db.close()

         return result
     }

     private fun addCursorItem(mentor: MentorDatabase) {
         val db: SQLiteDatabase = application.applicationContext.openOrCreateDatabase("Mentor", Context.MODE_PRIVATE,  null)
         db.execSQL("INSERT INTO Mentor VALUES ('${mentor.Id}', '${mentor.name}', '${mentor.firstname}', '${mentor.number}');")
         db.close()
     }

     private fun updateCursorItem(mentor: MentorDatabase, id:Long) {
        val db: SQLiteDatabase = application.applicationContext.openOrCreateDatabase("Mentor", Context.MODE_PRIVATE,  null)
        db.execSQL("UPDATE Mentor SET name = '${mentor.name}', firstname = '${mentor.firstname}', phone = '${mentor.number}' WHERE Id = '$id'")
        db.close()
     }

     private fun clearCursorItem(id:Long) {
        val db: SQLiteDatabase = application.applicationContext.openOrCreateDatabase("Mentor", Context.MODE_PRIVATE,  null)
        db.execSQL("DELETE FROM Mentor WHERE Id = '$id'")
        db.close()
     }

     fun getUnique(mentor: Mentor):MentorDatabase? {

         return _mentors?.value?.toMutableList()?.find {
             (it.name == mentor.name) and
                     (it.firstname == mentor.firstname)
         }
     }


}