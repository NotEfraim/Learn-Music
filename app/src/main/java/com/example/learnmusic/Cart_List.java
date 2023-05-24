package com.example.learnmusic;

public class Cart_List {

    String id;
    String user_id;
    String seller_email;
    String product_id;
    String product_name;
    String product_price;
    String product_category;
    String image_url;

    public Cart_List(String id, String user_id, String seller_email,
                     String product_id, String product_name, String product_price,
                     String product_category, String image_url){

        this.id = id;
        this.user_id = user_id;
        this.seller_email = seller_email;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_category = product_category;
        this.image_url = image_url;


    }

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
