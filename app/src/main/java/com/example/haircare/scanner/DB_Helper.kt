package com.example.haircare.scanner

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream

const val dbName = "db_cosmetics.db"
private const val TABLE_NAME = "COSMETICS"

private const val COL_NAME = "name"
private const val COL_ID = "_id"
private const val COL_DESCRIPTION = "description"


const val dbVersionNumber = 1

class DB_Helper(private val context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersionNumber) {
    private var dataBase: SQLiteDatabase? = null

    init {
        val dbExist = checkDatabase()
        if (dbExist) {
            //if copied just open
            openDatabase()
        } else {
            // copy database
            println("Doesn't exist")
            createDatabase()
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    private fun createDatabase() {
        copyDatabase()
    }

    private fun checkDatabase(): Boolean {
        val dbFile = File(context.getDatabasePath(dbName).path)
        println(context.getDatabasePath(dbName).path)
        return dbFile.exists()
    }

    private fun copyDatabase() {

        val inputStream = context.assets.open("databases/$dbName")
        val outputFile = File(context.getDatabasePath(dbName).path)
        val outputStream = FileOutputStream(outputFile)
        val bytesCopied = inputStream.copyTo(outputStream)
        println("************************ bytesCopied $bytesCopied")
        inputStream.close()

        outputStream.flush()
        outputStream.close()
    }

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
        val ingredient = "\'" + nameField.trim() + "\'"
        println(ingredient)
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COL_NAME =$ingredient"
        var cursor: Cursor? = null

        try {
            cursor = dataBase?.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            dataBase?.execSQL(selectQuery)
            return Cosmetics("error", 1, "nothing")

        }
        cursor?.moveToFirst()
        var id: Int
        var name: String
        var description: String

        if (cursor != null && cursor.getCount() > 0) {
            id = cursor.getInt(cursor.getColumnIndex(COL_ID))
            name = cursor.getString(cursor.getColumnIndex(COL_NAME))
            description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))

            return Cosmetics(id = id, description = description, name = name)
        }
        return Cosmetics("nic", 0, "niema :(")
    }

}
