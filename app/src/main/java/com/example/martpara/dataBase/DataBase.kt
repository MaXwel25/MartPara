package com.example.martpara.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Reminds::class],
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract fun dbDAO(): DBDAO

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            return INSTANCE ?: synchronized(DataBase::class.java) {
                buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            DataBase::class.java,   // исправлено
            "list_database"
        ).build()
    }
}