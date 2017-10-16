package com.stiliyan.phonebook.phonebook;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stiliyan.phonebook.phonebook.data.ContactVO;
import com.stiliyan.phonebook.phonebook.data.CountryVO;
import com.stiliyan.phonebook.phonebook.data.DataBase;
import com.stiliyan.phonebook.phonebook.data.DataController;
import com.stiliyan.phonebook.phonebook.utils.Validation;

import java.util.ArrayList;
import java.util.List;

public class EditContactActivity extends AppCompatActivity {

    private TextView nameTV;
    private TextView phoneTV;
    private TextView emailTV;
    private Spinner countrySpinner;
    private Spinner codeSpinner;
    private Spinner genderSpinner;
    private Button confirmBtn;

    List<CountryVO> countries;
    private ContactVO contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Intent intent = getIntent();
        final int id = intent.getIntExtra(DataBase.key_id, -1);

        nameTV = (TextView) findViewById(R.id.name);
        phoneTV = (TextView) findViewById(R.id.phone);
        emailTV = (TextView) findViewById(R.id.email);
        countrySpinner = (Spinner) findViewById(R.id.spinCountry);
        codeSpinner = (Spinner) findViewById(R.id.spinCode);
        genderSpinner = (Spinner) findViewById(R.id.spinGender);
        confirmBtn = (Button) findViewById(R.id.confirm);

        contact = DataController.getInstance().getContactById( id );
        int countryIndex = 0;

        List<String> countryNames = new ArrayList<>();
        List<String> countryCodes = new ArrayList<>();

        countries = DataController.getInstance().getAllCountries();
        for (int i = 0; i < countries.size(); i++)
        {
            if ( contact.country.id == countries.get(i).id )
                countryIndex = i;

            countryNames.add( countries.get(i).country_name);
            countryCodes.add( countries.get(i).code);
        }

        nameTV.setText( contact.name );
        phoneTV.setText( contact.phone );
        emailTV.setText( contact.email );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item, countryNames );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        countrySpinner.setAdapter( adapter );

        adapter = new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item, countryCodes );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        codeSpinner.setAdapter( adapter );

        ArrayAdapter<CharSequence> adapterG = ArrayAdapter.createFromResource( this,
                R.array.genders, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        genderSpinner.setAdapter( adapterG );

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                codeSpinner.setSelection( position );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        countrySpinner.setSelection( countryIndex );

        String[] genders =  getResources().getStringArray( R.array.genders );
        genderSpinner.setSelection(contact.gender.equals( genders[0] ) ? 0 : 1);

        confirmBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                showAlert();
            }
        });

    }

    private void showAlert()
    {
        new AlertDialog.Builder(this)
                .setTitle( getResources().getString( R.string.areYouSure ) )
                .setMessage( getResources().getString( R.string.uRonAwayAddContact ) )
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        saveContact();
                    }})
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void saveContact()
    {
        if( TextUtils.isEmpty( nameTV.getText()) ) {
            nameTV.setError("name required ");
            return;
        }

        if ( !Validation.isValidEmail( emailTV.getText() ) )
        {
            emailTV.setError( "invalid email" );
            return;
        }

        if ( !Validation.isValidPhone( phoneTV.getText() ) )
        {
            phoneTV.setError( "invalid phone" );
            return;
        }

        ContactVO contact = new ContactVO();
        contact.name = nameTV.getText().toString();
        contact.phone = phoneTV.getText().toString();
        contact.email = emailTV.getText().toString();
        contact.country.id = countries.get( countrySpinner.getSelectedItemPosition() ).id;
        contact.gender = genderSpinner.getSelectedItem().toString();

        DataController.getInstance().updateContact( contact );
        Toast.makeText(this, "successful update contact", Toast.LENGTH_SHORT).show();

        Intent returnIntent = new Intent();
        setResult( Activity.RESULT_OK,returnIntent );

        finish();
    }

}
