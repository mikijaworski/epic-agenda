package com.example.epicagendaapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;

public class ContactsManager {
    private final int CONTACT_PICKER_RESULT = 898;
    private Activity activity;
    
    public ContactsManager(Activity activity){
        this.activity = activity;
    }

    public void selectContact(){
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        this.activity.startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }


    /**
     * Llamada en el ActivityResult de nuestra activity para obtener el email del usuario
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data, onSelectedEmail onSelectedEmailHandler){
        if(requestCode == CONTACT_PICKER_RESULT){
            if (resultCode == Activity.RESULT_OK) {

                Cursor cursor = null;
                String email = "";
                try {
                    Uri result = data.getData();

                    String id = result.getLastPathSegment();

                    cursor = this.activity.getContentResolver().query(CommonDataKinds.Email.CONTENT_URI, null, CommonDataKinds.Email.CONTACT_ID + "=?", new String[] { id }, null);
                    int emailIdx = cursor.getColumnIndex(CommonDataKinds.Email.DATA);

                    if (cursor.moveToFirst())
                        email = cursor.getString(emailIdx);

                } catch (Exception e) {
                } finally {
                    if (cursor != null)
                        cursor.close();
                    if (email.length() == 0)
                        onSelectedEmailHandler.onFailure();
                    else
                        onSelectedEmailHandler.onSuccess(email);
                }

            } else {
                onSelectedEmailHandler.onFailure();
            }
        }
    }

    public interface onSelectedEmail{ void onSuccess(String email); void onFailure();}

}

