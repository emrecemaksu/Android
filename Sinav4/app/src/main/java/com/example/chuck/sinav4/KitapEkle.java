package com.example.chuck.sinav4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KitapEkle extends AppCompatActivity {
    DataHelper dataHelper;
    EditText edit_Ekle_Name, edit_Ekle_Yazar, edit_Ekle_Yil, edit_Ekle_Fiyat;
    int yil, fiyat;
    String name, yazar;
    Button btn_Kitap_Ekle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap_ekle);
        edit_Ekle_Fiyat = findViewById(R.id.edit_Ekle_Fiyat);
        edit_Ekle_Yazar = findViewById(R.id.edit_Ekle_Yazar);
        edit_Ekle_Yil = findViewById(R.id.edit_Ekle_Yil);
        edit_Ekle_Name = findViewById(R.id.edit_Ekle_Name);
        btn_Kitap_Ekle = findViewById(R.id.btn_Ekle);
        btn_Kitap_Ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHelper = new DataHelper(getApplicationContext());
                name = edit_Ekle_Name.getText().toString();
                yazar = edit_Ekle_Yazar.getText().toString();
                yil = edit_Ekle_Yil.getText().length();
                fiyat = edit_Ekle_Fiyat.getText().length();
                if(name.isEmpty() || yazar.isEmpty() || yil == 0 || fiyat == 0)
                {
                    Toast.makeText(getApplicationContext(), "Lütfen bütün değerleri girin...", Toast.LENGTH_LONG).show();

                }
                else
                {
                    long id = dataHelper.insertData(name, yazar, yil, fiyat);
                    if(id<=0)
                    {
                        Toast.makeText(getApplicationContext(), "Ekleme başarısız", Toast.LENGTH_LONG).show();
                        edit_Ekle_Fiyat.setText(null);
                        edit_Ekle_Name.setText("");
                        edit_Ekle_Yazar.setText("");
                        edit_Ekle_Yil.setText(null);
                    } else
                    {
                        Toast.makeText(getApplicationContext(), "Ekleme başarılı", Toast.LENGTH_LONG).show();
                        edit_Ekle_Fiyat.setText(null);
                        edit_Ekle_Name.setText("");
                        edit_Ekle_Yazar.setText("");
                        edit_Ekle_Yil.setText(null);
                        Intent intent = new Intent(getApplicationContext(), KitapListele.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
