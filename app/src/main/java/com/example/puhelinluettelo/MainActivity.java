package com.example.puhelinluettelo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String TAG = "QWERTY";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        parseJson();
        MainAdapter adapter = new MainAdapter(this,parseJson());
        listView.setAdapter(adapter);
    }

    private String getJson(){
        //Log.d(TAG,"getting json");
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

    private List<Contact> parseJson(){
        List<Contact> items = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(getJson());

            int i = 0;
            while(i < jsonArray.length()){
                JSONObject obj = jsonArray.getJSONObject(i);
                Log.d(TAG, obj.toString());
                JSONArray numbers = obj.getJSONArray("numbers");
                List<String> numberList = new ArrayList<>();

                int a = 0;
                while (a < numbers.length()){
                    Log.d(TAG, numbers.getString(a));
                    numberList.add(numbers.getString(a));
                    a++;
                }
                Contact contact = new Contact(obj.getString("firstName"),obj.getString("lastName"),numberList);
                items.add(contact);
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG,e.toString());
            return null;
        }
        return items;
    }
}
