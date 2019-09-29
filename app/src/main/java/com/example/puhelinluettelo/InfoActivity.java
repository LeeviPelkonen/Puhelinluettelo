package com.example.puhelinluettelo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    private Contact contact;
    private EditText firstName;
    private EditText lastName;
    private InfoAdapter adapter;
    private ListView listView;
    private Button addButton;
    private Button saveButton;
    private List<String> numberList;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //getting intent and the all the objects
        final Intent intent = getIntent();
        contact = intent.getParcelableExtra("contact");
        final int contactId = intent.getIntExtra("contactId",0);
        final List<Contact> contactList = intent.getParcelableArrayListExtra("contactList");
        numberList = contact.getNumbers();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        listView = findViewById(R.id.infoListView);
        addButton = findViewById(R.id.addNumberButton);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);

        firstName.setText(contact.getFirstName());
        lastName.setText(contact.getLastName());

        adapter = new InfoAdapter(this,contact);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                contact.addNumber("");
                adapter.notifyDataSetChanged();
            }
        });

        //saving the changes and passing the contactList to mainActivity
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
                Intent mintent = new Intent(InfoActivity.this,MainActivity.class);
                mintent.putExtra("contact",contact);
                mintent.putExtra("contactId",contactId);
                mintent.putParcelableArrayListExtra("contactList", (ArrayList<? extends Parcelable>) contactList);
                startActivity(mintent);
                finish();
            }
        });

        //deleting the current contact from the contactList and passing the list to mainActivity
        deleteButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                contactList.remove(contactId);
                Intent mintent = new Intent(InfoActivity.this,MainActivity.class);
                mintent.putParcelableArrayListExtra("contactList", (ArrayList<? extends Parcelable>) contactList);
                startActivity(mintent);
                finish();
            }
        });

        //creating toolbar with back button
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(contact.getFirstName() + " " + contact.getLastName());
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            //saving the changes and passing the contactList to mainActivity
            public void onClick(View view) {
                saveChanges();
                Intent mintent = new Intent(InfoActivity.this,MainActivity.class);
                mintent.putExtra("contact",contact);
                mintent.putExtra("contactId",contactId);
                mintent.putParcelableArrayListExtra("contactList", (ArrayList<? extends Parcelable>) contactList);
                startActivity(mintent);
                finish();
            }
        });
    }

    //saving name and setting first character uppercase
    private void saveChanges(){
        contact.setFirstName(firstName.getText().toString().substring(0,1).toUpperCase() + firstName.getText().toString().substring(1));
        contact.setLastName(lastName.getText().toString().substring(0,1).toUpperCase() + lastName.getText().toString().substring(1));
    }
}
