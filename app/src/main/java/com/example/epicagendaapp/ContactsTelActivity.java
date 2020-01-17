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
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_tel);

        contactesView = findViewById(R.id.contactesView);

        this.setupListViewListeners();
        contactes = getContacts2();

        contactesAdapter = new ContactAdapter(this, contactes);

        contactesView.setAdapter(contactesAdapter);
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

    public ArrayList<Contact> getContacts2() {
        ContentResolver contentResolver = getContentResolver();
        String contactId = null;
        String displayName = null;
        contactes = new ArrayList<>();
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    Contact contactsInfo = new Contact(contactId);

                    contactsInfo.setName(displayName);

                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);

                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        contactsInfo.setNumber(phoneNumber);
                    }

                    phoneCursor.close();

                    contactes.add(contactsInfo);
                }
            }
        }
        cursor.close();

        return contactes;
    }

    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> contactList = new ArrayList<>();
        ArrayList<Long> id_list = new ArrayList<>();

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
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
                        String photo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                        System.out.println(photo);
                        if (photo != null) {
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(photo));
                                c.setPhoto(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        c.setName(name);
                        c.setNumber(number);

                        if (!id_list.contains(Long.parseLong(id))) {
                            id_list.add(Long.parseLong(id));
                        }
                    }
                    pCur.close();
                }

                contactList.add(c);
            }
        }
        cur.close();

        return contactList;
    }

    public void ExportToFirebase(View view) {

    }

    public void AddNewContact(View view) {
        Intent intent = new Intent(this, AddNewContact.class);
        startActivity(intent);
    }
}
