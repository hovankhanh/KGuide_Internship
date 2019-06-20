package com.example.khanhho.kguide.Model;

public class Booking {
    private String currentTime, status, startDate, tourName, guideName,touristName;
    private int price;

    public Booking() {
    }

    public Booking(String currentTime, String status, String startDate, int price, String tourName, String guideName, String touristName) {
        this.currentTime = currentTime;
        this.status = status;
        this.startDate = startDate;
        this.price = price;
        this.tourName = tourName;
        this.guideName = guideName;
        this.touristName = touristName;
    }

    public String getTouristName() {
        return touristName;
    }

    public void setTouristName(String touristName) {
        this.touristName = touristName;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }
}
