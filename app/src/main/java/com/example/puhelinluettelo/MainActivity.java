package com.example.puhelinluettelo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Boolean firstNameFirst = true;
    private String TAG = "QWERTY";
    private ListView listView;
    private Intent intent;
    private List<Contact> items = new ArrayList<>();
    private MainAdapter adapter;
    private String alphabet = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        Button sortButton = findViewById(R.id.sortButton);
        Button newButton = findViewById(R.id.newButton);

        sortButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(firstNameFirst) {
                    firstNameFirst = false;
                    adapter.firstNameFirst = false;
                } else {
                    firstNameFirst = true;
                    adapter.firstNameFirst = true;
                }
                sortNames();
                getFirstLetters();
                adapter.notifyDataSetChanged();
            }
        });

        newButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Contact newContact = new Contact("", "", new ArrayList<String>());
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("contact",newContact);
                intent.putExtra("contactId",items.size());
                intent.putParcelableArrayListExtra("contactList", (ArrayList<? extends Parcelable>) items);
                items.add(newContact);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new MainAdapter(this,parseJson());
        listView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    //getting the json file from assets folder and saving it as a json.
    private String getJson(){
        String json = null;
        try {
            InputStream inputStream = getAssets().open("contacts.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,e.toString());
            return null;
        }
        return json;
    }

    //parsing the json and saving it as Contact object
    private List<Contact> parseJson() {
        intent = getIntent();
        Bundle extras = intent.getExtras();

        //checking if there are contacts saved to intent
        if (extras == null ) {
        try {
            JSONArray jsonArray = new JSONArray(getJson());
            int i = 0;
            while (i < jsonArray.length()) {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONArray numbers = obj.getJSONArray("numbers");
                List<String> numberList = new ArrayList<>();

                int a = 0;
                while (a < numbers.length()) {
                    numberList.add(numbers.getString(a));
                    a++;
                    }
                Contact contact = new Contact(obj.getString("firstName"), obj.getString("lastName"), numberList);
                items.add(contact);
                i++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, e.toString());
                return null;
            }
        }
        else {
            if(extras.containsKey("contactList")){
                items.clear();
                items = intent.getParcelableArrayListExtra("contactList");
            }
            if(extras.containsKey("contact")){
                Contact contact = intent.getParcelableExtra("contact");
                int contactId = intent.getIntExtra("contactId",0);
                items.remove(contactId);
                items.add(contactId,contact);
            }
        }
        sortNames();
        getFirstLetters();
        return items;
    }

    private void sortNames(){
        if (firstNameFirst){
            sortByFirstName();
        }else{
            sortByLastName();
        }
    }

    private void sortByFirstName(){
        Collections.sort(items, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return contact.getFirstName().compareTo(t1.getFirstName());
            }
        });
    }

    private void sortByLastName(){
        Collections.sort(items, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return contact.getLastName().compareTo(t1.getLastName());
            }
        });
    }

    //going through the list and setting the firstLetter Boolean to true for each new letter.
    private void getFirstLetters(){
        int i = 0;
        while(i < items.size()){
            if(firstNameFirst){
                if (alphabet.equals(items.get(i).getFirstName().substring(0,1))){
                    items.get(i).setFirstLetter(false);
                }else {
                    alphabet = items.get(i).getFirstName().substring(0, 1);
                    items.get(i).setFirstLetter(true);
                }
                //doing the same but for the lastName
            }else{
                if (alphabet.equals(items.get(i).getLastName().substring(0,1))){
                    items.get(i).setFirstLetter(false);
                }else {
                    alphabet = items.get(i).getLastName().substring(0, 1);
                    items.get(i).setFirstLetter(true);
                }
            }
            i++;
        }
    }
}
