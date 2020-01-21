package com.example.epicagendaapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.epicagendaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import models.Contact;
import models.ContactAdapter;

public class ReadContactsFirebaseActivity extends AppCompatActivity {

    private DatabaseReference userDatabase;
    private ListView contactesView;
    private ArrayList<Contact> contactes;
    private ContactAdapter contactesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_contacts_firebase);

        contactesView = findViewById(R.id.contactesView);

        //this.setupListViewListeners();


        contactesAdapter = new ContactAdapter(this, contactes);

        contactesView.setAdapter(contactesAdapter);

        userDatabase = FirebaseDatabase.getInstance().getReference("usuaris");
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Contact users = snapshot.getValue(Contact.class);
                    contactes.add(users);
                    Log.d("users",users.getName());
                }
                contactesAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userDatabase.addValueEventListener(userListener);
    }
   /* private void setupListViewListeners() {

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
    }*/
}
