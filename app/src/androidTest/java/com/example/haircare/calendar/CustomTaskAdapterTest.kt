package com.example.haircare.calendar

import android.content.Context
import android.provider.Telephony.Mms.Rate
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class CustomTaskAdapterTest {

    @Test
    fun ifTaskIsNotInDatabase_deleteReturnsFalse() {
        val task = Task("peh", "task", 0, 9999)
        val context = ApplicationProvider.getApplicationContext<Context>()
        val customTaskAdapter = CustomTaskAdapter(context)
        assertEquals(customTaskAdapter, false)
    }

/*    @Test
    fun ifTaskIsInDatabase_deleteReturnsTrue() {
        val task = Task("test", "test", 0, 2)
        val context = ApplicationProvider.getApplicationContext<Context>()
        val addTask = CustomTaskAdapter(context).addTask("test", "test", 0)
        val deleteTask = CustomTaskAdapter(context).deleteTask(task)
        assertEquals(deleteTask, true)
    }

    @Test
    fun ifPlanOnSpecificDayHasTask_getPlanReturnsNotEmptyList() {
        val day = 0
        val context = ApplicationProvider.getApplicationContext<Context>()
        val addTask = CustomTaskAdapter(context).addTask("test", "test", 0)
        val customTaskAdapter = CustomTaskAdapter(context).getPlanDay(day)
        assertEquals(customTaskAdapter.size, 1)
    }*/

}