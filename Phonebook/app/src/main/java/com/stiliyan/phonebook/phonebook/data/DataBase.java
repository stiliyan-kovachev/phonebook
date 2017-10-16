package com.stiliyan.phonebook.phonebook.data;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    private static final String database_name = "phonebookDB";
    private static final String contact_table_name = "phonebook";
    private static final String country_table_name = "country";

    private static final int table_version    = 1;

    public  static  final String key_id      = "id";
    public  static  final String key_contact_id  = "contact_id";
    public  static  final String key_name    = "name";
    public  static  final String key_email   = "email";
    public  static  final String key_country = "country";
    public  static  final String key_country_id = "country_id";
    public  static  final String key_code    = "code";
    public  static  final String key_phone   = "phone";
    public  static  final String key_gender  = "gender";

    private SQLiteDatabase db;


    public DataBase( Activity context ) {
        super( context, database_name, null, table_version );
    }

    public void addContact( ContentValues contact ) {
        open();

        db.insert(contact_table_name, null, contact );
        close();
    }

    public Cursor getAllContacts()
    {
        open();

        Cursor cursor =  db.query(contact_table_name, new String[]{ key_contact_id, key_name, key_phone },null, null, null, null, null );

//        close();

        return  cursor;

    }

    public Cursor getAllCountries()
    {
        open();

        Cursor cursor =  db.query(country_table_name, new String[]{ key_country_id, key_country, key_code },null, null, null, null, null );

//        close();

        return  cursor;

    }

    public void deleteContact( int id )
    {
        open();

//        db.rawQuery("delete from " + contact_table_name + " " + "where " + key_id + " = '" + id + "'", null );
        db.delete( contact_table_name, key_contact_id + "=" + id, null );
        close();
    }

    public Cursor getContactById( int id )
    {
        open();

        Cursor cursor = db.rawQuery( "select " + "*" + " from " + contact_table_name + " " + "where " + key_contact_id + " = '" + id + "'", null );

        return  cursor;

    }

    public Cursor getCountryById( int id )
    {
        open();

        Cursor cursor = db.rawQuery( "select " + "*" + " from " + country_table_name + " " + "where " + key_country_id + " = '" + id + "'", null );

        return  cursor;

    }

    public boolean hasoCountries()
    {
        open();

        int count = 0;
        Cursor c = db.rawQuery( "select count(*) from " + country_table_name, null );
        if( c.moveToFirst() ) {
            count = c.getInt( 0 );
        }
        close();

        return count > 0;
    }

    public void addCountry( ContentValues contact )
    {
        open();

        db.insert( country_table_name, null, contact );
        close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ contact_table_name + " (" +
                key_contact_id + " integer primary key autoincrement," +
                key_name + " text not null, " +
                key_email + " text not null, " +
                key_country_id + " integer, " +
                key_phone + " text not null, " +
                key_gender + " text not null " + ");");

        db.execSQL("create table "+ country_table_name + " (" +
                key_country_id + " integer primary key autoincrement," +
                key_country + " text not null, " +
                key_code + " text not null " + ");");
    }

    @Override
    public void onUpgrade( SQLiteDatabase sqLiteDatabase, int i, int i1 ) {

    }

    public void open() throws SQLException {
        db = getWritableDatabase();
    }

    public void close() throws SQLException
    {
        db.close();
    }
}
