package com.zxwl.homestart.Bean;

import android.graphics.drawable.Drawable;

/**
 * Created by sks on 2018/4/19.
 */

public class VpitemBean {

    private CharSequence name;
    private CharSequence pckename;
    private Drawable image;
    private Drawable backimage;

    public VpitemBean(CharSequence name, CharSequence pckename, Drawable image, Drawable backimage) {
        this.name = name;
        this.pckename = pckename;
        this.image = image;
        this.backimage = backimage;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public CharSequence getPckename() {
        return pckename;
    }

    public void setPckename(CharSequence pckename) {
        this.pckename = pckename;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public Drawable getBackimage() {
        return backimage;
    }

    public void setBackimage(Drawable backimage) {
        this.backimage = backimage;
    }
}
