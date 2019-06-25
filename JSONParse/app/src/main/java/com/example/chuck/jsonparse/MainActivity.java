package com.example.chuck.jsonparse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = "{\n" +
                        "\"name\":\"John\",\n" +
                        "\"age\":30,\n" +
                        "\"cars\":[ \"Ford\", \"BMW\", \"Fiat\" ]\n" +
                        "}";
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String name = jsonObject.getString("name");
                    int age = jsonObject.getInt("age");
                    JSONArray cars = jsonObject.getJSONArray("cars");
                    for (int i = 0; i < cars.length(); i++) {
                        System.out.println(cars.getString(i));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                /*
                String JSONString = "{\"employee\":{\"name\":\"Sachin\",\"salary\":56000}}";
                try {
                    JSONObject jsonObject = new JSONObject(JSONString).getJSONObject("employee");;
                    String name = jsonObject.getString("name");
                    int salary = jsonObject.getInt("salary");
                    System.out.println(name + " " + String.valueOf(salary));
                    //System.out.println(jsonObject.getString("employee"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                */
            }
        });
    }
}