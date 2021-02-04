package com.example.haircare.calendar

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//testy
// androidTest, inaczej niz w przypadku zwykłego testu odpalane są przez emulator, poniewaz potrzebują komponentów androida
@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TaskDatabase
    private lateinit var dao: TaskDao

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), TaskDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.taskDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertTask() = runBlocking {
        val task = TaskEntity("task", "peh", 0, 1)
        dao.insertTask(task)

        val allTask = dao.readAllTask().getOrAwaitValue()
        assertEquals(allTask.contains(task), true)
    }

    @Test
    fun deleteTask() = runBlocking {
        val task = TaskEntity("task", "peh", 0, 1)
        dao.insertTask(task)
        dao.deleteTask(task)
        val allTask = dao.readAllTask().getOrAwaitValue()
        assertEquals(allTask.contains(task), false)
    }

    @Test
    fun findListOfTaskForSpecificDay() = runBlocking {
        val task1 = TaskEntity("task1", "peh1", 0, 1)
        val task2 = TaskEntity("task2", "peh2", 0, 2)
        val task3 = TaskEntity("task3", "peh3", 1, 3)
        dao.insertTask(task1)
        dao.insertTask(task2)
        dao.insertTask(task3)

        val allTaskForSpecificDay = dao.observeAllTaskAtDay(0).getOrAwaitValue()
        assertEquals(allTaskForSpecificDay.contains(task3), false)
    }
}