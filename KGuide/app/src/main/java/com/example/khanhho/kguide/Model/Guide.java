package com.example.khanhho.kguide.Model;

public class Guide {

    private String address;
    private String country;
    private String dayofbirth;
    private String email;
    private String gender;
    private String jobposition;
    private String language;
    private String name;
    private String phonenumber;
    private String surname;
    private String status;
    private String image;
    private int star;

    public Guide(String address, String country, String dayofbirth, String email, String gender, String jobposition, String language, String name, String phonenumber, String surname, String status, String image, int star) {
        this.address = address;
        this.country = country;
        this.dayofbirth = dayofbirth;
        this.email = email;
        this.gender = gender;
        this.jobposition = jobposition;
        this.language = language;
        this.name = name;
        this.phonenumber = phonenumber;
        this.surname = surname;
        this.status = status;
        this.image = image;
        this.star = star;


    }

    public Guide() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDayofbirth() {
        return dayofbirth;
    }

    public void setDayofbirth(String dayofbirth) {
        this.dayofbirth = dayofbirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJobposition() {
        return jobposition;
    }

    public void setJobposition(String jobposition) {
        this.jobposition = jobposition;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
