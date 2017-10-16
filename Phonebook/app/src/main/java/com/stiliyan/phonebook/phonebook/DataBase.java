package com.stiliyan.phonebook.phonebook;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    private static final String database_name = "phonebookDB";
    private static final String table_name    = "phonebook";
    private static final int table_version    = 1;

    private  static  final String key_id    = "id";
    private  static  final String key_name    = "name";
    private  static  final String key_email   = "email";
    private  static  final String key_country = "country";
    private  static  final String key_code    = "code";
    private  static  final String key_phone   = "phone";
    private  static  final String key_gender  = "gender";

    private SQLiteDatabase db;


    public DataBase(Activity context ) {
        super( context, database_name, null, table_version );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ table_name + " (" +
                key_id + " integer primary key autoincrement," +
                key_name + " text not null, " +
                key_email + " text not null, " +
                key_country + " text not null, " +
                key_code + " text not null, " +
                key_phone + " text not null, " +
                key_gender + " text not null " + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void open() throws SQLException {
        db = getWritableDatabase();
    }

    public void close() throws SQLException
    {
        db.close();
    }
}
