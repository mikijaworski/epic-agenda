package com.example.epicagendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import models.Contact;
import models.ContactAdapter;

public class ContactsTelActivity extends AppCompatActivity {

    private ListView contactesView;
    private ArrayList<Contact> contactes;
    private ContactAdapter contactesAdapter;

    ImageButton import_firebase;
    Firebase firebase;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_tel);

        contactesView = findViewById(R.id.contactesView);

        this.setupListViewListeners();
        contactes = getContacts();
        contactesAdapter = new ContactAdapter(this, contactes);
        contactesView.setAdapter(contactesAdapter);

        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://epicagenda.firebaseio.com/usuaris");

        import_firebase = findViewById(R.id.import_firebase);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            import_firebase.setEnabled(false);
        }
    }

    private void setupListViewListeners() {

        contactesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
            }
        });

        contactesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //items.remove(position);
                //writeItems();
                return true;
            }
        });
    }

    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> contactList = new ArrayList<>();

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Contact c = new Contact(id);

               if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        c.setName(name);
                        c.setNumber(number);
                    }
                    pCur.close();
                }

                contactList.add(c);
            }
        }
        cur.close();

        System.out.println(contactList.size());

        return contactList;
    }

    public void ExportToFirebase(View view) {

        for (Contact contact: contactes) {
            firebase.push().setValue(contact);
        }
    }

    public void AddNewContact(View view) {
        Intent intent = new Intent(this, AddNewContact.class);
        startActivity(intent);
    }

    public void GoBack(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
}
