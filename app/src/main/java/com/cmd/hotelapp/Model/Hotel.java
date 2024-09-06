package com.cmd.hotelapp.Model;

public class Hotel {
    private String name;
    private String description;
    private String price;
    private String address;
    private String image;
    private String imgRoom;
    private String descriptionRoom;
    private double sale;
    private double quality;

    public Hotel() {

    }

    public Hotel(String name, String description, String price, String address, String descriptionRoom, String image, String imgRoom, double sale, double quality) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.address = address;
        this.image = image;
        this.imgRoom = imgRoom;
        this.descriptionRoom = descriptionRoom;
        this.sale = sale;
        this.quality = quality;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public String getImgRoom() {
        return imgRoom;
    }

    public String getDescriptionRoom() { return descriptionRoom; }

    public double getSale(){
        return sale;
    }

    public double getQuality() {
        return quality;
    }
}

