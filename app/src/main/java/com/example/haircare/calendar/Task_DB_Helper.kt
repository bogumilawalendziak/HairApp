package com.example.haircare.calendar

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.haircare.scanner.dbName
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

const val task_dbName = "db_tasks.db"
private const val task_dbVersion = 1
private const val TASK_TABLE_NAME = "TASKS"

private const val COL_NAME = "name"
private const val COL_ID = "_id"
private const val COL_PRODUCT= "product"
private const val COL_DESCRIPTION= "description"


class Task_DB_Helper(private val context: Context) : SQLiteOpenHelper(context, task_dbName, null, task_dbVersion) {

    private var dataBase: SQLiteDatabase? = null

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


    //check if db exist

    init {
        val dbExist = checkIfDbExist()
        if (dbExist){
            openDataBase()
            println("***** db exist *******")
        }
        else {
            createDataBase()
            println("***** db doesn't exist *******")
        }
    }

    private fun checkIfDbExist(): Boolean {
        val dbFile = File(context.getDatabasePath(task_dbName).path)
        return dbFile.exists()
    }

    private fun openDataBase() {
        dataBase =
            SQLiteDatabase.openDatabase(context.getDatabasePath(task_dbName).path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    private fun createDataBase() {
        val inputStream: InputStream = context.assets.open("databases/${task_dbName}")
        val outputFile = File(context.getDatabasePath(task_dbName).path)
        val outputStream = FileOutputStream(outputFile)
        val bytesCopied = inputStream.copyTo(outputStream)
        println("************************ $bytesCopied")
        inputStream.close()
        outputStream.flush()
        outputStream.close()
    }

    // Close the database.
    override fun close() {
        dataBase?.close()
        super.close()
    }

    fun viewTasks(task: Int): Task {
// query & cursor
        println(task.toString())
        val selectQuery = " SELECT * FROM $TASK_TABLE_NAME WHERE $COL_ID = $task"
        var cursor: Cursor? = null

        //checking
        try {
            cursor = dataBase?.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            dataBase?.execSQL(selectQuery)
            //return null

        }
        cursor?.moveToFirst()

        return if (cursor != null && cursor.count > 0) {
            cursor.getInt(cursor.getColumnIndex(COL_ID))
            var name: String = cursor.getString(cursor.getColumnIndex(COL_NAME))
            println(name)
            var product: String = cursor.getString(cursor.getColumnIndex(COL_PRODUCT))
            println(product)
            var description: String = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
            println(description)

            Task( product = product, name = name,description = description)
        } else Task( "error", "not found","sorry")

    }

}