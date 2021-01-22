package com.example.haircare.calendar

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.haircare.customplan.CustomPlan

const val DATABASE_VERSION = 2
const val DATABASE_NAME = "DATABASE"
const val TABLE_NAME = "TABLE_NAME"
const val COL_CUSTOM_NAME = "NAME"
const val COL_CUSTOM_PEH = "PEH"
const val COL_CUSTOM_DAY = "DAY"

class CustomTaskAdapter(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME (" +
                "_ID INTEGER PRIMARY KEY," +
                "$COL_CUSTOM_NAME TEXT," +
                "$COL_CUSTOM_PEH TEXT," +
                "$COL_CUSTOM_DAY INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getPlanDay(day: Int): MutableList<CustomPlan> {
        val list: MutableList<CustomPlan> = mutableListOf()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COL_CUSTOM_DAY=$day"
        var cursor: Cursor?
        try {
            cursor = db?.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db?.execSQL(selectQuery)
            return list
        }

        if (cursor?.moveToFirst() == true) {
            do {
                if (cursor != null && cursor.count > 0) {
                    val day = cursor.getInt(cursor.getColumnIndex(COL_CUSTOM_DAY))
                    val name = cursor.getString(cursor.getColumnIndex(COL_CUSTOM_NAME))
                    val peh = cursor.getString(cursor.getColumnIndex(COL_CUSTOM_PEH))
                    val id = cursor.getLong(cursor.getColumnIndex("_ID"))

                    list.add(CustomPlan(peh = peh, task = name, day = day, id = id))
                    //return list
                }
            } while (cursor.moveToNext())
        }
        return list
    }

    fun addCustomPlanTask(task: String, peh: String, day: Int): Long? {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_CUSTOM_NAME, task)
            put(COL_CUSTOM_PEH, peh)
            put(COL_CUSTOM_DAY, day)
        }

        val success = db?.insert("TABLE_NAME", null, values)
        db.close()
        return success
    }

    fun deleteCustomPlanTask(customPlan: CustomPlan) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COL_CUSTOM_NAME, customPlan.task)

        }
    }

}