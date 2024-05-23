package com.alikhan.projecttrial

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Create the database table
        db.execSQL(CREATE_TABLE_ITEMS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Upgrade the database schema if needed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ITEMS")
        onCreate(db)
    }

    companion object {
        // Database version
        private const val DATABASE_VERSION = 1

        // Database name
        private const val DATABASE_NAME = "ItemsDB"

        // Table name and column names
        const val TABLE_ITEMS = "items"
        const val COLUMN_ID = "id"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_PRICE = "price"
        const val COLUMN_IMAGE_URL = "imageUrl"
        const val COLUMN_QUANTITY = "quantity"

        // SQL statement to create the table
        private const val CREATE_TABLE_ITEMS = (
                "CREATE TABLE $TABLE_ITEMS (" +
                        "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "$COLUMN_DESCRIPTION TEXT," +
                        "$COLUMN_PRICE TEXT," +
                        "$COLUMN_IMAGE_URL TEXT," +
                        "$COLUMN_QUANTITY INTEGER" +
                        ")"
                )
    }
}
