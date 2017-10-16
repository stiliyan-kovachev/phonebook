package com.stiliyan.phonebook.phonebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.stiliyan.phonebook.phonebook.adapter.ContactListAdapter;
import com.stiliyan.phonebook.phonebook.data.ContactVO;
import com.stiliyan.phonebook.phonebook.data.CountryVO;
import com.stiliyan.phonebook.phonebook.data.DataBase;
import com.stiliyan.phonebook.phonebook.data.DataController;
import com.stiliyan.phonebook.phonebook.utils.RequestCodes;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button addContactBtn;
    private ListView contactList;
    private ContactListAdapter contactListAdapter;

    private List<ContactVO> contactData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addContactBtn = ( Button ) findViewById( R.id.addContact );
        contactList = ( ListView ) findViewById( R.id.contactList );

        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent( MainActivity.this, AddContactActivity.class );
                startActivityForResult( intent, RequestCodes.CREATE_EDIT_CONTACT_REQUEST_CODE );
            }
        });

        DataController.getInstance().setContext( this );
        if ( !DataController.getInstance().hasCountries() )
        {
            String[] countries = getResources().getStringArray(R.array.countries);
            String[] codes = getResources().getStringArray(R.array.country_codes);
            for ( int i = 0; i < countries.length; i++ )
            {
                CountryVO country = new CountryVO();
                country.country_name = countries[i];
                country.code = codes[i];

                DataController.getInstance().addCountry( country );
            }
        }
        contactData = DataController.getInstance().getAllContacts();

        contactListAdapter = new ContactListAdapter( this, R.layout.contact_list_item_renderer, contactData );
        contactList.setAdapter( contactListAdapter );

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContactVO model = ( ContactVO ) contactList.getItemAtPosition( i );

                Intent intent = new Intent( MainActivity.this, ViewContactActivity.class );
                intent.putExtra( DataBase.key_id, model.id );
                startActivityForResult( intent, RequestCodes.CREATE_EDIT_CONTACT_REQUEST_CODE );
            }
        });

        contactList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick( AdapterView<?> arg0, View v,
                                          final int i, long l ) {

                final CharSequence[] items = { "Edit", "Delete" };

                AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this );

                builder.setTitle("Action:");
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        ContactVO model = ( ContactVO ) contactList.getItemAtPosition( i );
                        if( item == 0 )
                        {
                            Intent intent = new Intent( MainActivity.this, AddContactActivity.class );
                            intent.putExtra( DataBase.key_id, model.id );
                            startActivityForResult( intent, RequestCodes.CREATE_EDIT_CONTACT_REQUEST_CODE );
                        }
                        else
                        if ( item == 1 ) {
                            DataController.getInstance().deleteContact( model.id );
                            updateList();
                        }
                    }

                });

                AlertDialog alert = builder.create();

                alert.show();

                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( requestCode == RequestCodes.CREATE_EDIT_CONTACT_REQUEST_CODE ) {
            if(resultCode == Activity.RESULT_OK)
                updateList();
        }
    }

    private void updateList()
    {
        contactData.clear();
        contactData.addAll(  DataController.getInstance().getAllContacts() );
        contactListAdapter.notifyDataSetChanged();
    }
}
