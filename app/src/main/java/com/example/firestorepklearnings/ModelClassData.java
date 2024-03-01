package com.example.firestorepklearnings;

import java.io.Serializable;

public class ModelClassData implements Serializable {
        String name;
        String countrycode;
        String phone;
        String email;
        String password;

        public ModelClassData(){}

        public ModelClassData(String name, String countrycode, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.countrycode = countrycode;

        }

        public ModelClassData(String name, String countrycode, String phone, String email, String password) {
            this.name = name;
            this.countrycode = countrycode;
            this.phone = phone;
            this.email = email;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountrycode() {
            return countrycode;
        }

        public void setCountrycode(String countrycode) {
            this.countrycode = countrycode;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


