package ru.easycode.zerotoheroandroidtdd

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "records", primaryKeys = ["id"])
data class RecordEntity(
    val id: Long,
    @ColumnInfo(name = "text_record") val text: String,
)
