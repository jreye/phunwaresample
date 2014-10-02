package com.phunware.sample.app.model;

import android.graphics.Bitmap;

/**
 * Created by julio on 10/1/14.
 */
public class Location {
    private Bitmap image;
    private String address;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
