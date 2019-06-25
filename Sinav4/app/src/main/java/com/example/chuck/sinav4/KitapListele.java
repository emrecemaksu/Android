package com.example.chuck.sinav4;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class KitapListele extends AppCompatActivity {
    private static final String DATABASE_NAME = "myKitaplik";    // Database Name
    private static final String TABLE_NAME = "myTable";   // Table Name
    private static final int DATABASE_Version = 1;    // Database Version
    private static final String UID="id";     // Column I (Primary Key)
    private static final String NAME = "Name";    //Column II
    private static final String YAZAR = "Yazar";    // Column III
    private static final String YIL = "Yıl";
    private static final String FIYAT = "Fiyat";
    private Context context;
    ListView listView;
    static SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap_listele);
        listView = findViewById(R.id.listView);
        final ArrayList<String> kitapName = new ArrayList<String>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, kitapName);
        listView.setAdapter(arrayAdapter);
        try {
            sqLiteDatabase = this.openOrCreateDatabase("myKitaplik", MODE_PRIVATE, null);
            String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                    " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+ YAZAR+" VARCHAR(225) ,"+ YIL+ " INTEGER, "+FIYAT+ " INTEGER);";
            sqLiteDatabase.execSQL(CREATE_TABLE);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM myTable", null);
            int nameIx = cursor.getColumnIndex("Name");
            cursor.moveToFirst();
            while (cursor != null){
                kitapName.add(cursor.getString(nameIx));
                cursor.moveToNext();
                arrayAdapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), KitapDuzenle.class);
                intent.putExtra("info", "old");
                intent.putExtra("name", kitapName.get(position));
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }
}
