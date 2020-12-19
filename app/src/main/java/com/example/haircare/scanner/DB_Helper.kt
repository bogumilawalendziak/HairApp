package com.example.haircare.scanner

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream

const val dbName = "db_cosmetics"
private const val TABLE_NAME = "COSMETICS"
private const val COL_NAME = "name"
private const val COL_ID = "_id"
private const val COL_DESCRIPTION = "description"
const val dbVersionNumber = 1

class DB_Helper(private val context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersionNumber) {
    private var dataBase: SQLiteDatabase? = null

    init { //Checking if database is same as in asset folder
        val dbExist = checkDatabase()
        if (dbExist) {
            openDatabase()
        } else {
            println("Database doesn't exist")
            createDatabase()
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // copyDatabase()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    private fun createDatabase() {
        copyDatabase()
    }


    // Check if the database already copied to the device.
    private fun checkDatabase(): Boolean {
        val dbFile = File(context.getDatabasePath(dbName).path)
        println(context.getDatabasePath(dbName).path)
        return dbFile.exists()
    }

    // Copy the database
    private fun copyDatabase() {

        val inputStream = context.assets.open("databases/db_cosmetics.db")
        val outputFile = File(context.getDatabasePath(dbName).path)
        val outputStream = FileOutputStream(outputFile)
        val bytesCopied = inputStream.copyTo(outputStream)
        println("bytes copied $bytesCopied")
        inputStream.close()

        outputStream.flush()
        outputStream.close()
    }

    // Open the database with read and write access mode.
    private fun openDatabase() {
        dataBase =
            SQLiteDatabase.openDatabase(context.getDatabasePath(dbName).path, null, SQLiteDatabase.OPEN_READWRITE)

    }

    // Close the database.
    override fun close() {
        dataBase?.close()
        super.close()
    }

    fun viewCosmetic(nameField: String): Cosmetics {
        val szukaneslowo = "\'" + nameField + "\'"
        println(szukaneslowo)
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COL_NAME =$szukaneslowo"
        val cosm: Cosmetics

        var cursor: Cursor?

        try {
            cursor = dataBase?.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            dataBase?.execSQL(selectQuery)
            return Cosmetics("nothing", 0, "error")
        }
        cursor?.moveToFirst()
        var id: Int
        var name: String
        var description: String


        if (cursor != null) {
            id = cursor.getInt(cursor.getColumnIndex(COL_ID))
            name = cursor.getString(cursor.getColumnIndex(COL_NAME))
            description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
            cosm = Cosmetics(id = id, description = description, name = name)

            return cosm
        }
        return Cosmetics("nothing", 0, "error")
    }
}