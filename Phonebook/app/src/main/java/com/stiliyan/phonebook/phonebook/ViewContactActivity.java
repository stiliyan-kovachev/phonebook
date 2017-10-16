package com.stiliyan.phonebook.phonebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.stiliyan.phonebook.phonebook.data.ContactVO;
import com.stiliyan.phonebook.phonebook.data.DataBase;
import com.stiliyan.phonebook.phonebook.data.DataController;
import com.stiliyan.phonebook.phonebook.utils.Consts;

import org.w3c.dom.Text;

public class ViewContactActivity extends AppCompatActivity {

    private TextView nameTV;
    private TextView countryTV;
    private TextView codeTV;
    private TextView phoneTV;
    private TextView emailTV;
    private TextView genderTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        Intent intent = getIntent();
        final int id = intent.getIntExtra( Consts.ID, -1 );

        nameTV = ( TextView ) findViewById( R.id.name );
        countryTV = ( TextView ) findViewById( R.id.country );
        codeTV = ( TextView ) findViewById( R.id.code );
        phoneTV = ( TextView ) findViewById( R.id.phone );
        emailTV = ( TextView ) findViewById( R.id.email );
        genderTV = ( TextView ) findViewById( R.id.gender );

        ContactVO contact = DataController.getInstance().getContactById( id );

        nameTV.setText( contact.name );
        countryTV.setText( contact.country.country_name );
        codeTV.setText( contact.country.code );
        phoneTV.setText( contact.phone );
        emailTV.setText( contact.email );
        genderTV.setText( contact.gender );
    }

}
