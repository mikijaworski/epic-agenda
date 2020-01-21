package com.example.epicagendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewContact extends AppCompatActivity {

    EditText contact_name;
    EditText contact_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        contact_name = findViewById(R.id.contact_name);
        contact_number = findViewById(R.id.contact_number);
    }

    public void AddContact(View view) {

        if (contact_name.getText().toString().equals("") && contact_number.getText().toString().equals("")) {
            Toast.makeText(this, "You can't leave the name and number empty!", Toast.LENGTH_SHORT).show();
        } else if (contact_name.getText().toString().equals("")) {
            Toast.makeText(this, "You can't leave the name empty!", Toast.LENGTH_SHORT).show();
        } else if (contact_number.getText().toString().equals("")){
            Toast.makeText(this, "You can't leave the number empty!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            intent.putExtra(ContactsContract.Intents.Insert.NAME, contact_name.getText());
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, contact_number.getText());
            startActivityForResult(intent, 1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1)
        {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Added Contact", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled Added Contact", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Cancel(View view) {
        Intent intent = new Intent(this, ContactsTelActivity.class);
        startActivity(intent);
    }
}
