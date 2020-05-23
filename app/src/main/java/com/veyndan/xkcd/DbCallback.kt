package com.veyndan.xkcd

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.db.SupportSQLiteOpenHelper

class DbCallback : SupportSQLiteOpenHelper.Callback(1) {

    override fun onCreate(db: SupportSQLiteDatabase) {}

    override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}
