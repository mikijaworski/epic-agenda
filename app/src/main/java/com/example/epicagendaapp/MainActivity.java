package com.example.epicagendaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView contactesView;
    private ArrayList<String> contactes;
    private ArrayAdapter contactesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactesView = findViewById(R.id.contactesView);

        this.setupListViewListeners();
        contactes = new ArrayList<>();

        contactesAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contactes);

        contactesView.setAdapter(contactesAdapter);
        contactes.add("First Item");
        contactes.add("Second Item");
    }

    private void setupListViewListeners() {

        contactesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                //OpenItem(view, position);
            }
        });

        contactesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //items.remove(position);
                //itemsAdapter.notifyDataSetChanged();
                //writeItems();
                return true;
            }
        });
    }
}
