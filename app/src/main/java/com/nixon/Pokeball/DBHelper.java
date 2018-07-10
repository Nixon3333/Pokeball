package com.nixon.Pokeball;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "pokemonsTable";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";


    public static final String DATABASE_NAME = "pokemonsDB";
    public static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ("
                + KEY_ID + " integer primary key autoincrement,"
                + KEY_NAME + " text" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
