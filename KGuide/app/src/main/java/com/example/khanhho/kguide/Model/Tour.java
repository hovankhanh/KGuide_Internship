package com.example.khanhho.kguide.Model;

public class Tour {
    private int imageTour;
    private String nameTour;
    private int priceTour;

    public Tour(int imageTour, String nameTour, int priceTour) {
        this.imageTour = imageTour;
        this.nameTour = nameTour;
        this.priceTour = priceTour;
    }

    public int getImageTour() {
        return imageTour;
    }

    public void setImageTour(int imageTour) {
        this.imageTour = imageTour;
    }

    public String getNameTour() {
        return nameTour;
    }

    public void setNameTour(String nameTour) {
        this.nameTour = nameTour;
    }

    public int getPriceTour() {
        return priceTour;
    }

    public void setPriceTour(int priceTour) {
        this.priceTour = priceTour;
    }
}
