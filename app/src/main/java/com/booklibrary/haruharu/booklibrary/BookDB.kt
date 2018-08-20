package com.booklibrary.haruharu.booklibrary

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BookDB : SQLiteOpenHelper {
    private val DB_VERSION=1
    private val DB_NAME="MyBook"

    constructor(context:Context?) : super(context,"MyBook",null,1) {}


    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL("CREATE TABLE IF NOT EXISTS MyBook(id integer primary key autoincrement, title text,link text,image text,author text,price integer,discount integer,publisher text,description text,numOfPage INT,readedPage INT )")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS MyBook")
        onCreate(p0)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }
}