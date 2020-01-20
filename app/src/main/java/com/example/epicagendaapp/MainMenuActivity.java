package com.example.epicagendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void OpenMobileContacts(View view) {
        Intent intent = new Intent(this, ContactsTelActivity.class);
        startActivity(intent);
    }

    public void OpenFireBaseContacts(View view) {
        Intent intent = new Intent(this, FirebaseMain.class);
        startActivity(intent);
    }
}
