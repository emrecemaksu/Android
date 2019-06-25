package com.example.chuck.basitsqliteornek;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView, textView2;
    EditText editName, editText6, editText3, editPass, editText5;
    Button button2, button, button3, button4;
    MyDbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.editName);
        editPass = findViewById(R.id.editPass);
        helper = new MyDbHelper(getApplicationContext());
    }
    public void delete(View view){

    }
    public void update(View view){
    }
    public void addUser(View view){
        String nameDeger = editName.getText().toString();
        String passDeger = editPass.getText().toString();
        long id = helper.insertData(nameDeger, passDeger);
        if(id<=0){
            Toast.makeText(this, "Kayıt Yapılmadı...", Toast.LENGTH_SHORT).show();
            editName.setText("");
            editPass.setText("");
        }else{
            Toast.makeText(this, "Kayıt Yapıldı...", Toast.LENGTH_SHORT).show();
            editName.setText("");
            editPass.setText("");
        }
    }
    public void viewdata(View view){
        String helperi = helper.getData();
        Log.w("Datalar", helperi);
    }
}
