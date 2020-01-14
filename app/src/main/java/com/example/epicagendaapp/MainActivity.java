package com.example.epicagendaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.epicagendaapp.ui.login.LoginFirebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OpenMobileContacts(View view) {
        Intent intent = new Intent(this, ContactsTelActivity.class);
        startActivity(intent);
    }

    public void OpenFirebaseContacts(View view) {
        Intent intent = new Intent(this, LoginFirebase.class);
        startActivity(intent);
    }
}
