package models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.epicagendaapp.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private List<Contact> items;

    public ContactAdapter(@NonNull Context context, ArrayList<Contact> contactList) {
        super(context, 0, contactList);
        this.context = context;
        this.items = contactList;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.contact_layout, parent, false);
        }

        Contact contact = items.get(pos);

        TextView name = view.findViewById(R.id.contact_name);
        name.setText(contact.getName());

        return view;

    }
}
