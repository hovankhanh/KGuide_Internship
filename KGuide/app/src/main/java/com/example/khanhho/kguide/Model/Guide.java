package com.example.khanhho.kguide.Model;

public class Guide {

    private String nameGuide;

    // Image name (Without extension)
    private String avarta;
    private int star;
    private String id;

    public Guide(String nameGuide, String avarta, int star, String id) {
        this.nameGuide = nameGuide;
        this.avarta = avarta;
        this.star = star;
        this.id = id;
    }

    public Guide(String nameGuide, String avarta, int star) {
        this.nameGuide = nameGuide;
        this.avarta = avarta;
        this.star = star;
    }

    public String getNameGuide() {
        return nameGuide;
    }

    public void setNameGuide(String nameGuide) {
        this.nameGuide = nameGuide;
    }

    public String getAvarta() {
        return avarta;
    }

    public void setAvarta(String avarta) {
        this.avarta = avarta;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
