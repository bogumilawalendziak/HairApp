package com.example.haircare.calendar

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM task_table")
    fun readAllTask(): LiveData<MutableList<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE date =:date")
    fun observeAllTaskAtDay(date: String): LiveData<MutableList<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE date =:date")
    fun observeAllTaskAt30Day(date: String): LiveData<MutableList<TaskEntity>>
}