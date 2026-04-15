package com.example.martpara.dataBase
import kotlinx.coroutines.flow.Flow
interface DB_repository {
    fun getReminds(): Flow<List<Reminds>>
    suspend fun insertRemind(remind: Reminds)
    suspend fun deleteRemind(remind: Reminds)
}