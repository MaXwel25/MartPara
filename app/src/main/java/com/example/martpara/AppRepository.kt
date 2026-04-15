package com.example.martpara

import androidx.lifecycle.LiveData
import com.example.martpara.dataBase.DataBase
import com.example.martpara.dataBase.Reminds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class AppRepository {
    companion object{
        private var INSTANCE: AppRepository?=null

        fun getInstance(): AppRepository {
            if (INSTANCE == null )
            {
                INSTANCE = AppRepository()
            }
            return INSTANCE ?:
            throw IllegalStateException("репозиторий не иниц.")

        }
    }

    val myDB by lazy {
        com.example.martpara.dataBase.DB_repository(
            DataBase.Companion.getDatabase(
                ReminderApplication.context
            )
        )
    }

    private val myCoroutineScope = CoroutineScope(Dispatchers.Main) // работа в основном потоке

    val listReminds: LiveData<List<Reminds>> = myDB.getReminds().asLiveData()

    fun addRemind(remind: Reminds) {
        myCoroutineScope.launch {
            myDB.insertRemind(remind)
        }
    }

    fun addRemind(remind: Reminds)
    {
        myCoroutineScope.launch {
            myDB.insertRemind(remind)
        }
    }

    fun deleteRemind(remind: Reminds) {
        myCoroutineScope.launch {
            myDB.deleteRemind(remind)
        }
    }

    fun onDestroy() {
        myCoroutineScope.cancel()
    }
}