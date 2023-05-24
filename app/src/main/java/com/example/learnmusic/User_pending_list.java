package com.example.learnmusic;

public class User_pending_list {
    private String id;
    private String seller_email;
    private String user_id;
    private String product_id;
    private String product_name;
    private String product_price;
    private String product_category;
    private String image_url;


    public User_pending_list(String id, String seller_email, String user_id, String product_id, String product_name, String product_price,
                             String product_category,  String image_url) {
        this.seller_email = seller_email;
        this.user_id = user_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_category = product_category;
        this.image_url = image_url;
        this.id = id;
    }

    // GETTER


    public String getId() {
        return id;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getProduct_id() {
        return product_id;
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

    public String getImage_url() {
        return image_url;
    }

}
