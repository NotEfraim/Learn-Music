package com.example.learnmusic;

public class Pending_transaction_List {

    private String Product_id;
    private String Product_name;
    private String Product_price;
    private String Product_category;
    private int image_url;

    public Pending_transaction_List(String product_id, String product_name, String product_price, String product_category, int image_url) {
        Product_id = product_id;
        Product_name = product_name;
        Product_price = product_price;
        Product_category = product_category;
        this.image_url = image_url;
    }

    // GETTER
    public String getProduct_id() {
        return Product_id;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public String getProduct_price() {
        return Product_price;
    }

    public String getProduct_category() {
        return Product_category;
    }

    public int getImage_url() {
        return image_url;
    }

    //Setter


    public void setProduct_id(String product_id) {
        Product_id = product_id;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public void setProduct_price(String product_price) {
        Product_price = product_price;
    }

    public void setProduct_category(String product_category) {
        Product_category = product_category;
    }

    public void setImage_url(int image_url) {
        this.image_url = image_url;
    }


}
