package com.example.epicagendaapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.epicagendaapp.FirebaseLogin;
import com.example.epicagendaapp.FirebaseMain;
import com.example.epicagendaapp.MainMenuActivity;
import com.example.epicagendaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import models.Contact;
import models.ContactAdapter;

public class ReadContactsFirebaseActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ListView contactesView;
    private ArrayList<Contact> contactes;
    private ContactAdapter contactesAdapter;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_contacts_firebase);

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, FirebaseLogin.class);
            startActivity(intent);
        } else {
            contactes = new ArrayList<>();

            mDatabase = FirebaseDatabase.getInstance().getReference("usuaris");

            ValueEventListener userListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        String splited[] = ds.getValue().toString().split(", ");
                        System.out.println(splited.length);
                        System.out.println(ds.getValue().toString());
                        if (splited.length == 3) {
                            String id = splited[2].split("=")[1];
                            String name = splited[1].split("=")[1];
                            String number = splited[0].split("=")[1];
                            contactes.add(new Contact(id, name, number));
                        }
                    }

                    contactesAdapter.notifyDataSetChanged();
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mDatabase.addValueEventListener(userListener);

            contactesView = findViewById(R.id.contactesView);

            //this.setupListViewListeners();

            contactesAdapter = new ContactAdapter(this, contactes);

            contactesView.setAdapter(contactesAdapter);

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

    public void MainMenu(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    public void OpenFirebaseSettings(View view) {
        Intent intent = new Intent(this, FirebaseMain.class);
        startActivity(intent);
    }
}
