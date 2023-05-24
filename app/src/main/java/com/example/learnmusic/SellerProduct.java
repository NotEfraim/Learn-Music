package com.example.learnmusic;

public class SellerProduct {

    private String product_name;
    private String product_price;
    private String product_category;
    private int product_pic;

    public SellerProduct(String product_name, String product_price, String product_category, int product_pic ) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_category = product_category;
        this.product_pic = product_pic;
    }

    //GETTER

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_category() {
        return product_category;
    }

    public int getProduct_pic() {
        return product_pic;
    }

    //Setter

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public void setProduct_pic(int product_pic) {
        this.product_pic = product_pic;
    }
}
