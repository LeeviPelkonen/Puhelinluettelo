package com.example.puhelinluettelo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import androidx.annotation.NonNull;

class MainAdapter extends ArrayAdapter<Contact> {

    private Boolean firstNameFirst;
    private Context context;
    private List<Contact> contactList;

    MainAdapter(Context c, List<Contact> contactList){
        super(c, R.layout.list_item, R.id.itemButton);

        this.context = c;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Contact contact = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Button name = convertView.findViewById(R.id.itemButton);

        if(firstNameFirst){
            name.setText(contact.getFirstName() + " " + contact.getLastName());
        }else{
            name.setText(contact.getLastName() + " " + contact.getFirstName());
        }

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact myContact = (Contact) view.getTag();
                //TODO use the contact
                Log.d("testing",myContact.getFirstName());
            }
        });

        return convertView;
    }
}
