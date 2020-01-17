package models;

import android.graphics.Bitmap;

public class Contact {

    private String id;
    private String name;
    private Bitmap photo;
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Contact(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
