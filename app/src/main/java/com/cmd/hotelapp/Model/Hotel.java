package com.cmd.hotelapp.Model;

public class Hotel {
    private String name;
    private String description;
    private String price; // Có thể thay đổi thành double nếu cần
    private String address;
    private String image; // URI hoặc URL của hình ảnh khách sạn
    private String imgRoom; // URI hoặc URL của hình ảnh phòng
    private String descriptionRoom; // Mô tả phòng
    private double sale; // Giảm giá
    private double quality; // Đánh giá chất lượng

    public Hotel() {
        // Constructor rỗng
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

    // Các phương thức getter
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

    public String getDescriptionRoom() {
        return descriptionRoom;
    }

    public double getSale() {
        return sale;
    }

    public double getQuality() {
        return quality;
    }

    // Các phương thức setter
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImgRoom(String imgRoom) {
        this.imgRoom = imgRoom;
    }

    public void setDescriptionRoom(String descriptionRoom) {
        this.descriptionRoom = descriptionRoom;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    // Phương thức toString()
    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", imgRoom='" + imgRoom + '\'' +
                ", descriptionRoom='" + descriptionRoom + '\'' +
                ", sale=" + sale +
                ", quality=" + quality +
                '}';
    }

    // Phương thức tính giá sau giảm giá
    public double getDiscountedPrice() {
        return Double.parseDouble(price) * (1 - sale / 100);
    }
}
