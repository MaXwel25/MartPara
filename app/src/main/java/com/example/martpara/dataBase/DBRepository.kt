package com.example.martpara.dataBase

import kotlin.coroutines.flow.Flow

class DBRepository(val dao: DBDAO) : DB_repository {
    override fun getReminds(): Flow<List<Reminds>> = dao.getReminds()
    override suspend fun insertRemind(remind: Reminds) = dao.insertRemind(remind)
    override suspend fun deleteRemind(remind: Reminds) = dao.deleteRemind(remind)

}