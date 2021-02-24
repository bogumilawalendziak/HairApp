package com.example.haircare.calendar

import androidx.lifecycle.LiveData

class TaskRepository ( private val taskDao: TaskDao){
    val readALlTask: LiveData<MutableList<TaskEntity>> =taskDao.readAllTask()

    suspend fun addTask(taskEntity: TaskEntity){
        taskDao.insertTask(taskEntity)
    }
    fun getTaskAtDay(date:String): LiveData<MutableList<TaskEntity>> {
        return taskDao.observeAllTaskAtDay(date)
    }
    suspend fun deleteTask(taskEntity: TaskEntity){
        taskDao.deleteTask(taskEntity)
    }
}