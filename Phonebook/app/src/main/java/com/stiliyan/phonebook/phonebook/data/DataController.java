package com.stiliyan.phonebook.phonebook.data;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DataController {

    private DataBase db;
    private static DataController instance;

    public static DataController getInstance() {
        if ( instance == null )
            instance = new DataController();

        return instance;
    }

    private DataController() {

    }

    public void setContext( Activity context ) {
        db = new DataBase( context );
    }

    public void addContact( ContactVO contact ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.key_name, contact.name );
        contentValues.put( DataBase.key_email, contact.email );
        contentValues.put( DataBase.key_country_id, contact.country.id );
        contentValues.put( DataBase.key_phone, contact.phone );
        contentValues.put( DataBase.key_gender, contact.gender );

        db.addContact( contentValues );
    }

    public void updateContact( ContactVO contact ) {

        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.key_name, contact.name );
        contentValues.put( DataBase.key_email, contact.email );
        contentValues.put( DataBase.key_country_id, contact.country.id );
        contentValues.put( DataBase.key_phone, contact.phone );
        contentValues.put( DataBase.key_gender, contact.gender );

        db.updateContact( contact.id, contentValues );
    }

    public void addCountry( CountryVO country ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.key_country, country.country_name );
        contentValues.put( DataBase.key_code, country.code );

        db.addCountry( contentValues );
    }

    public boolean hasCountries() {
        return  db.hasoCountries();
    }

    public List<ContactVO> getAllContacts() {
        List<ContactVO> allContacts = new ArrayList<>();

        Cursor c = db.getAllContacts();
        if ( c.moveToFirst() )
            do {

                ContactVO model = new ContactVO();
                model.id =  ( c.getInt(c.getColumnIndex( DataBase.key_contact_id ) ) );
                model.name = ( c.getString(c.getColumnIndex( DataBase.key_name ) ) );
                model.phone = ( c.getString(c.getColumnIndex( DataBase.key_phone ) ) );

                Cursor cr = db.getCountryById( model.country.id );
                if ( cr.moveToFirst() ) {
                    model.country.code = cr.getString(cr.getColumnIndex(DataBase.key_code));
                    model.country.country_name = cr.getString(cr.getColumnIndex(DataBase.key_country));
                }

                allContacts.add( model);
            }
            while ( c.moveToNext() );

        db.close();

        return allContacts;
    }

    public List<CountryVO> getAllCountries() {
        List<CountryVO> allContacts = new ArrayList<>();

        Cursor c = db.getAllCountries();
        if ( c.moveToFirst() )
            do {

                CountryVO model = new CountryVO();
                model.id =  ( c.getInt(c.getColumnIndex( DataBase.key_country_id ) ) );
                model.country_name = ( c.getString(c.getColumnIndex( DataBase.key_country ) ) );
                model.code = ( c.getString(c.getColumnIndex( DataBase.key_code ) ) );

                allContacts.add( model);
            }
            while ( c.moveToNext() );

        db.close();

        return allContacts;
    }

    public ContactVO getContactById( int id ) {
        ContactVO model = new ContactVO();

        Cursor c = db.getContactById( id );
        if ( c.moveToFirst() ) {
            model.id =  ( c.getInt(c.getColumnIndex( DataBase.key_contact_id ) ) );
            model.name = ( c.getString(c.getColumnIndex( DataBase.key_name ) ) );
            model.country.id = ( c.getInt(c.getColumnIndex( DataBase.key_country_id ) ) );
            model.email = ( c.getString(c.getColumnIndex( DataBase.key_email ) ) );
            model.phone = ( c.getString(c.getColumnIndex( DataBase.key_phone ) ) );
            model.gender = ( c.getString(c.getColumnIndex( DataBase.key_gender ) ) );

            Cursor cr = db.getCountryById( model.country.id );
            if ( cr.moveToFirst() ) {
                model.country.code = cr.getString(cr.getColumnIndex(DataBase.key_code));
                model.country.country_name = cr.getString(cr.getColumnIndex(DataBase.key_country));
            }
        }

        db.close();

        return model;
    }

    public  void deleteContact( int id )
    {
        db.deleteContact( id );
    }
}
