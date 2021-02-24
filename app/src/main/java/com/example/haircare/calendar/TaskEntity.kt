package com.example.haircare.calendar

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskEntity(
    var task: String,
    var peh: String,
    var date: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
) {
}