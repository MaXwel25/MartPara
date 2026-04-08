package com.example.martpara.dataBase

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query          // добавить эту строку
import kotlinx.coroutines.flow.Flow

interface DBDAO {
    @Query("SELECT * FROM Reminds order by datetime")
    fun getReminds(): Flow<List<Reminds>>

    @Insert(entity = Reminds::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemind(remind: Reminds)

    @Delete(entity = Reminds::class)
    suspend fun deleteRemind(remind: Reminds)
}