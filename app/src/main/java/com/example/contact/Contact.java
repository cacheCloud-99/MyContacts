package com.example.contact;

import java.io.Serializable;

public class Contact implements Serializable {
    public Contact(String name, String number, String image) {
        this.name = name;
        this.number = number;
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String name;
    private String number;
    private String image;

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getImage() {
        return image;
    }

    public Contact(){

    }
}
