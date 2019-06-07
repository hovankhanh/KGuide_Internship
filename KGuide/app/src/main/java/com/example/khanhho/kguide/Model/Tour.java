package com.example.khanhho.kguide.Model;

public class Tour {
    private String imageTour;
    private String description;
    private String city;
    private String language;
    private String name;
    private String topic;
    private int price;
    private String service;
    private String age;
    private String time;

    public Tour() {
    }

    public Tour(String imageTour, String description, String city, String language, String name, String topic, int price, String service, String age, String time) {
        this.imageTour = imageTour;
        this.description = description;
        this.city = city;
        this.language = language;
        this.name = name;
        this.topic = topic;
        this.price = price;
        this.service = service;
        this.age = age;
        this.time = time;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getImageTour() {
        return imageTour;
    }

    public void setImageTour(String imageTour) {
        this.imageTour = imageTour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
