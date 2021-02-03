package com.example.haircare.calendar

import androidx.lifecycle.LiveData

class TaskRepository ( private val taskDao: TaskDao){
    val readALlTask: LiveData<MutableList<TaskEntity>> =taskDao.readAllTask()

    suspend fun addTask(taskEntity: TaskEntity){
        print(" dodano task : ${taskEntity.day} oraz id ${taskEntity.id}, ${taskEntity.task}")
        taskDao.insertTask(taskEntity)
    }
    fun getTaskAtDay(day:Int): LiveData<MutableList<TaskEntity>> {
        return taskDao.observeAllTaskAtDay(day)
    }
    suspend fun deleteTask(taskEntity: TaskEntity){
        taskDao.deleteTask(taskEntity)
    }
}