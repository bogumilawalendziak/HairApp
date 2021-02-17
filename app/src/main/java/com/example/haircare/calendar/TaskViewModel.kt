package com.example.haircare.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    private val repository = TaskRepository(taskDao)
    val readAllTask = repository.readALlTask

    fun addTask(taskEntity: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(taskEntity)
            print(" dodano task : ${taskEntity.day} oraz id ${taskEntity.id}, ${taskEntity.task}")
        }
    }

    fun getTasksAtDay(day: Int): LiveData<MutableList<TaskEntity>> {

        return repository.getTaskAtDay(day)

    }

    fun deleteTask(taskEntity: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
           repository.deleteTask(taskEntity)
        }

    }
}