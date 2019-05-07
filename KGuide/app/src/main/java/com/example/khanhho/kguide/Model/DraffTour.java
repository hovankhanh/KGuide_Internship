package com.example.khanhho.kguide.Model;

public class DraffTour {
    private String description;
    private String city;
    private String language;
    private String name;
    private String topic;
    private int price;

    public DraffTour() {
    }

    public DraffTour(String description, String city, String language, String name, String topic, int price) {
        this.description = description;
        this.city = city;
        this.language = language;
        this.name = name;
        this.topic = topic;
        this.price = price;
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
