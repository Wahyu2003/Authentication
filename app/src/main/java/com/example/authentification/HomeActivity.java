package com.example.authentification;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private Button btnKeluar;
    private TextView welcomeMessage;
    private DataBaseHeleperLogin db;

    public static final String SHARED_PREF_NAME = "myPref";

    private SharedPreferences sharedPreferences;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnKeluar = findViewById(R.id.btnKeluar);
        welcomeMessage = findViewById(R.id.welcomeMessage);

        db = new DataBaseHeleperLogin(this);
        //sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(Login.SHARED_PREF_NAME, MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        welcomeMessage.setText("Selamat datang, " + username);
        Boolean checksession = db.checkSession("ada");
        if (checksession == false){
            Intent login = new Intent(getApplicationContext(), Login.class);
            startActivity(login);
            finish();
        }

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean updateSeesion = db.upgradeSession("kosong", 1);
                if (updateSeesion == true){
                    Toast.makeText(getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("masuk", false);
                    editor.apply();

                    Intent keluar = new Intent(getApplicationContext(), Login.class);
                    startActivity(keluar);
                    finish();
                }
            }
        });
    }
}