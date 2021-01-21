package com.example.haircare.calendar

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.haircare.customplan.CustomPlan

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "DATABASE"
const val TABLE_NAME = "TABLE_NAME"
const val COL_CUSTOM_NAME = "NAME"
const val COL_CUSTOM_PEH = "PEH"
const val COL_CUSTOM_DAY = "DAY"

class CustomTaskAdapter(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private var dataBase: SQLiteDatabase? = null


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME (" +
                "COL_ID INTEGER PRIMARY KEY," +
                "$COL_CUSTOM_NAME TEXT," +
                "$COL_CUSTOM_PEH TEXT," +
                "$COL_CUSTOM_DAY INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private fun getPlanDay(day: Int): CustomPlan {
        //val db =this.writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COL_CUSTOM_DAY=$day"
        var cursor: Cursor?

        try {
            cursor = dataBase?.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            dataBase?.execSQL(selectQuery)
            return CustomPlan("error", "nothing", 0)

        }
        cursor?.moveToFirst()

        if (cursor != null && cursor.count > 0) {
            val day = cursor.getInt(cursor.getColumnIndex(COL_CUSTOM_DAY)).toInt()
            val name = cursor.getString(cursor.getColumnIndex(COL_CUSTOM_NAME))
            val peh = cursor.getString(cursor.getColumnIndex(COL_CUSTOM_PEH))

            return CustomPlan(peh = peh, task = name, day = day)
        }
        return CustomPlan("error", "nothing", 0)
    }

    fun addCustomPlanTask(customPlan: CustomPlan): Long? {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COL_CUSTOM_NAME, customPlan.task)
            put(COL_CUSTOM_PEH, customPlan.peh)
            put(COL_CUSTOM_DAY, customPlan.day)

        }

        val success = db?.insert("TABLE_NAME", null, values)
        db.close()
        return success
    }

}