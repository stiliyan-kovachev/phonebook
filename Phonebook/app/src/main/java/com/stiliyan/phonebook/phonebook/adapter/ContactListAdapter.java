package com.stiliyan.phonebook.phonebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stiliyan.phonebook.phonebook.R;
import com.stiliyan.phonebook.phonebook.data.ContactVO;

import java.util.List;

public class ContactListAdapter extends ArrayAdapter<ContactVO> {

    public ContactListAdapter( Context context, int textViewResourceId ) {
        super( context, textViewResourceId );
    }

    public ContactListAdapter( Context context, int resource, List<ContactVO> items ) {
        super( context, resource, items );
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {

        View v = convertView;

        if ( v == null ) {
            LayoutInflater vi;
            vi = LayoutInflater.from( getContext() );
            v = vi.inflate( R.layout.contact_list_item_renderer, null );
        }

        ContactVO p = getItem( position );
        if ( p != null ) {
            TextView name = ( TextView ) v.findViewById(R.id.name);
            TextView number = ( TextView ) v.findViewById(R.id.phone);

            if (name != null) {
                name.setText( p.name );
            }

            if (number != null) {
                number.setText( p.phone );
            }
        }

        return v;
    }
}
