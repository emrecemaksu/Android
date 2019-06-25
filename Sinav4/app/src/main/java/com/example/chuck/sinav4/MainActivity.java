package com.example.chuck.sinav4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_Ekle, btn_Listele;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_Ekle = findViewById(R.id.btn_Ekle);
        btn_Listele = findViewById(R.id.btn_Listele);
        btn_Ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KitapEkle.class);
                startActivity(intent);
            }
        });
        btn_Listele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KitapListele.class);
                startActivity(intent);
            }
        });
    }
}
