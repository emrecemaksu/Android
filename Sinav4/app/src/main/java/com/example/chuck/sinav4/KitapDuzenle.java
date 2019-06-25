package com.example.chuck.sinav4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KitapDuzenle extends AppCompatActivity {
    EditText edit_Degistir_Name, edit_Degistir_Yazar, edit_Degistir_Yil, edit_Degistir_Fiyat;
    Button btn_Degistir_Sil, btn_Degistir_Duzenle;
    static SQLiteDatabase sqLiteDatabase;
    DataHelper dataHelper;
    String name, yil, yazar, fiyat;
    private static final String TABLE_NAME = "myTable";   // Table Name
    private static final int DATABASE_Version = 1;    // Database Version
    private static final String UID="id";     // Column I (Primary Key)
    private static final String NAME = "Name";    //Column II
    private static final String YAZAR = "Yazar";    // Column III
    private static final String YIL = "Yıl";
    private static final String FIYAT = "Fiyat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap_duzenle);
        edit_Degistir_Name = findViewById(R.id.edit_Degistir_Name);
        edit_Degistir_Fiyat = findViewById(R.id.edit_Degistir_Fiyat);
        edit_Degistir_Yil = findViewById(R.id.edit_Degistir_Yil);
        edit_Degistir_Yazar = findViewById(R.id.edit_Ekle_Yazar);
        btn_Degistir_Sil = findViewById(R.id.btn_Degistir_Sil);
        btn_Degistir_Duzenle = findViewById(R.id.btn_Degistir_Duzenle);
        try {
            sqLiteDatabase = this.openOrCreateDatabase("myKitaplik", MODE_PRIVATE, null);
            String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                    " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+ YAZAR+" VARCHAR(225) ,"+ YIL+ " INTEGER, "+FIYAT+ " INTEGER);";
            sqLiteDatabase.execSQL(CREATE_TABLE);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM myTable", null);
            int nameIx = cursor.getColumnIndex("Name");
            int yazarIx = cursor.getColumnIndex("Yazar");
            int yilIx = cursor.getColumnIndex("Yıl");
            int fiyatIx = cursor.getColumnIndex("Fiyat");
            cursor.moveToFirst();
            while (cursor != null){
                name = cursor.getString(nameIx);
                yazar = cursor.getString(yazarIx);
                yil = cursor.getString(yilIx);
                fiyat = cursor.getString(fiyatIx);
                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        btn_Degistir_Sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = edit_Degistir_Name.getText().toString();
                String unYazar = edit_Degistir_Yazar.getText().toString();
                String unYil = edit_Degistir_Yil.getText().toString();
                String unFiyat = edit_Degistir_Fiyat.getText().toString();
                dataHelper = new DataHelper(getApplicationContext());
                if(uname.isEmpty() || unYazar.isEmpty() || unFiyat.isEmpty() || unYil.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Tekrar Gir...", Toast.LENGTH_LONG).show();
                    edit_Degistir_Fiyat.setText("");
                    edit_Degistir_Name.setText("");
                    edit_Degistir_Yazar.setText("");
                    edit_Degistir_Yil.setText("");
                }
                else{
                    boolean a= dataHelper.delete(uname, unFiyat, unYil, unYazar);
                    if(a == false)
                    {
                        Toast.makeText(getApplicationContext(), "Basarısız...", Toast.LENGTH_LONG).show();
                        edit_Degistir_Fiyat.setText("");
                        edit_Degistir_Name.setText("");
                        edit_Degistir_Yazar.setText("");
                        edit_Degistir_Yil.setText("");
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Silindi...", Toast.LENGTH_LONG).show();
                        edit_Degistir_Fiyat.setText("");
                        edit_Degistir_Name.setText("");
                        edit_Degistir_Yazar.setText("");
                        edit_Degistir_Yil.setText("");
                    }
                }
            }
        });
        btn_Degistir_Duzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(KitapDuzenle.this, "Düzenlendi...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
