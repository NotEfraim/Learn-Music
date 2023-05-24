package com.example.learnmusic;

public class MarketProductList {

    private String seller_email;
    private String Instrument_name;
    private String Instrument_price;
    private String Instrument_description;
    private String image_url;
    private String id;

    public MarketProductList(String id,String seller_email, String instrument_name, String instrument_price, String instrument_description, String image_url) {

        this.id = id;
        this.Instrument_name = instrument_name;
        this.Instrument_price = instrument_price;
        this.Instrument_description = instrument_description;
        this.image_url = image_url;
        this.seller_email = seller_email;

    }


    // Getter


    public String getId() {
        return id;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getInstrument_name() {
        return Instrument_name;
    }

    public String getInstrument_price() {
        return Instrument_price;
    }

    public String getInstrument_description() {
        return Instrument_description;
    }



    // Setter
    public void setInstrument_name(String instrument_name) {
        Instrument_name = instrument_name;
    }

    public void setInstrument_price(String instrument_price) {
        Instrument_price = instrument_price;
    }

    public void setInstrument_description(String instrument_description) {
        Instrument_description = instrument_description;
    }
}
