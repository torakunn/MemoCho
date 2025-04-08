package com.example.memocho.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// モデルの定義（Jsonシリアライザと似た感じで、データベースのカラム名とkotlinのデータ型を対応付けて変換するためのもの）
@Entity(tableName = "memo")
data class Memo (
    @PrimaryKey(autoGenerate = true)  val id: Long = 0L, // idが0の時、自動で割り振ってくれる
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "content") val content: String? = null,
)