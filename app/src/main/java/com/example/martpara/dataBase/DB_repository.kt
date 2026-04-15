package com.example.martpara.dataBase

interface DB_repository {
    fun getReminds(): Flow<List<Reminds>>
    suspend fun insertRemind(remind: Reminds)
    suspend fun deleteRemind(remind: Reminds)
}