package com.example.authentification;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView usernameText, nameText, githubLinkText;
    private Button editProfileButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.profileImage);
        usernameText = findViewById(R.id.username);
        nameText = findViewById(R.id.name);
        githubLinkText = findViewById(R.id.githubLink);
        editProfileButton = findViewById(R.id.editProfileButton);

        sharedPreferences = getSharedPreferences(Login.SHARED_PREF_NAME, MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String name = sharedPreferences.getString("name", "");
        String githubLink = sharedPreferences.getString("githubLink", "");

        usernameText.setText(username);
        nameText.setText(name);
        githubLinkText.setText(githubLink);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Membuat dialog untuk meminta kata sandi
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
                alertDialogBuilder.setTitle("Verifikasi Kata Sandi");

                // Menggunakan layout khusus untuk dialog
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_verify_password, null);
                alertDialogBuilder.setView(dialogView);

                final EditText passwordInput = dialogView.findViewById(R.id.passwordInput);

                alertDialogBuilder.setPositiveButton("Verifikasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String enteredPassword = passwordInput.getText().toString();
                        String storedPassword = sharedPreferences.getString("password", ""); // Mendapatkan kata sandi yang tersimpan

                        if (enteredPassword.equals(storedPassword)) {
                            // Kata sandi benar, izinkan pengguna untuk mengedit profil
                            // Implementasikan logika edit profil di sini
                            dialogInterface.dismiss();
                        } else {
                            // Kata sandi salah, tampilkan pesan kesalahan
                            Toast.makeText(ProfileActivity.this, "Kata Sandi Salah", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alertDialogBuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}
