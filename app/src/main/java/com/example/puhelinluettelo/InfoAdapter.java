package com.example.puhelinluettelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InfoAdapter extends ArrayAdapter<String> {

    private Contact myContact;
    private Context context;

    InfoAdapter(Context c, Contact contact){
        super(c,0,contact.getNumbers());
        context = c;
        myContact = contact;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //getting the selected contact object
        final String number = getItem(position);

        //checking if convertView isnt set and setting new view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.info_list_item, parent, false);
        }

        final TextView textView = convertView.findViewById(R.id.number);
        Button button = convertView.findViewById(R.id.removeButton);
        Button saveButton = convertView.findViewById(R.id.saveNumberButton);

        //removing number
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                myContact.removeNumber(position);
                notifyDataSetChanged();
            }
        });

        //changing number
        saveButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                myContact.changeNumber(textView.getText().toString(),position);
            }
        });
        textView.setText(number);
        return convertView;
    }
}
