package com.cmd.hotelapp.Model;

public class Hotel {
    private String name;
    private String description;
    private double price;
    private String image;
    private double sale;
    private double quality;

    public Hotel() {
        // Firestore yêu cầu constructor rỗng
    }

    public Hotel(String name, String description, double price, String imageUrl, double sale, double quality) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.sale = sale;
        this.quality = quality;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public double getSale(){
        return sale;
    }

    public double getQuality() {
        return quality;
    }
}

