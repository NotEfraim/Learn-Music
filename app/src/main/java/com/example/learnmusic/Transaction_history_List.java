package com.example.learnmusic;

public class Transaction_history_List {

    private String buyer_email;
    private String buyer_name;
    private String product_name;
    private String product_price;
    private String product_category;
    private String img_url;
//    private String user_id;

    public Transaction_history_List(String buyer_email,String buyer_name, String product_name, String product_price, String product_category, String img_url) {
        this.buyer_email = buyer_email;
        this.buyer_name = buyer_name;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_category = product_category;
        this.img_url = img_url;
//        this.user_id = user_id;
    }

    //getter


    public String getBuyer_email() {
        return buyer_email;
    }

    public String getBuyer_name() {
        return buyer_name;
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

    public String getImg_url() {
        return img_url;
    }


    //setter

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
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

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }



}