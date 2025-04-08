package com.example.memocho.data

import com.example.memocho.data.local.MemoDao
import com.example.memocho.data.model.Memo

interface MemoRepository {
    suspend fun getAll(): List<Memo>
    suspend fun loadAllByIds(noteIds: IntArray): List<Memo>
    suspend fun findByName(title: String): Memo
    suspend fun getNoteById(id: Long): Memo
    suspend fun insertAll(vararg users: Memo)
    suspend fun delete(memo: Memo)
    suspend fun updateNote(memo: Memo)
}


class LocalMemoRepository(private val memoDao: MemoDao) : MemoRepository {
    override suspend fun getAll(): List<Memo> = memoDao.getAll()
    override suspend fun loadAllByIds(noteIds: IntArray): List<Memo> = memoDao.loadAllByIds(noteIds)
    override suspend fun findByName(title: String): Memo = memoDao.findByName(title)
    override suspend fun getNoteById(id: Long): Memo = memoDao.getNoteById(id)
    override suspend fun insertAll(vararg users: Memo) = memoDao.insertAll(*users)
    override suspend fun delete(memo: Memo) = memoDao.delete(memo)
    override suspend fun updateNote(memo: Memo) = memoDao.updateNote(memo)
}