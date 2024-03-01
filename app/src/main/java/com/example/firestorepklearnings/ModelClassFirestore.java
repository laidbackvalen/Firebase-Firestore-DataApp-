package com.example.firestorepklearnings;

import java.io.Serializable;

public class ModelClassFirestore implements Serializable {
    public ModelClassFirestore(){

    }



    String city;
    String state;
    String country;
    String name;
    String phone;
    String email;
    String url;

    public ModelClassFirestore(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }
    public ModelClassFirestore(String city, String state, String country, String name, String phone, String email, String url) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.url = url;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
