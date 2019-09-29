package com.example.puhelinluettelo;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


class MainAdapter extends ArrayAdapter<Contact> {

    Boolean firstNameFirst = true;
    private Context context;
    private List<Contact> items;

    MainAdapter(Context c, List<Contact> contactList){
        super(c,0,contactList);
        context = c;
        items = contactList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //getting the selected contact object
        final Contact contact = getItem(position);

        //checking if convertView isnt set and setting new view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Button name = convertView.findViewById(R.id.itemButton);
        TextView textView = convertView.findViewById(R.id.alph);

        //changing the name order
        if(firstNameFirst){
            if (contact.getFirstLetter()){
                textView.setText(contact.getFirstName().substring(0,1).toUpperCase());
                textView.setVisibility(View.VISIBLE);
            }else{
                textView.setText("");
                textView.setVisibility(View.GONE);
            }
            name.setText(contact.getFirstName() + " " + contact.getLastName());
        }else{
            if (contact.getFirstLetter()){
                textView.setText(contact.getLastName().substring(0,1).toUpperCase());
                textView.setVisibility(View.VISIBLE);
            }else{
                textView.setText("");
                textView.setVisibility(View.GONE);
            }
            name.setText(contact.getLastName() + " " + contact.getFirstName());
        }

        //item on click listener
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating new intent and passing the selected contact to infoActivity
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("contact",contact);
                intent.putExtra("contactId",position);
                intent.putParcelableArrayListExtra("contactList", (ArrayList<? extends Parcelable>) items);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
