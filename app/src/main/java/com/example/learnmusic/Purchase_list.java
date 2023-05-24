package com.example.learnmusic;

public class Purchase_list {

    private String buyer_email;
    private String product_name;
    private String product_price;
    private String product_category;
    private String name;
    private String number;
    private String address;
    private String img_url;

    public Purchase_list(String buyer_email, String product_name, String product_price, String product_category, String name,
                         String number, String address, String img_url) {

        this.buyer_email = buyer_email;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_category = product_category;
        this.name = name;
        this.number = number;
        this.address = address;
        this.img_url = img_url;

    }

    // getter


    public String getBuyer_email() {
        return buyer_email;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_category() {
        return product_category;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getImg_url() {
        return img_url;
    }





    // setter


    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }



}
