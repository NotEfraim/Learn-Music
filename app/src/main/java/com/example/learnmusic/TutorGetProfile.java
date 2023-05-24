package com.example.learnmusic;

public class TutorGetProfile {

    private String id;
    private String name;
    private String age;
    private String gender;
    private String mobileNumber;
    private String emailAddress;
    private String location;
    private String address;
    private String image_url;



    public TutorGetProfile(String id, String name, String age, String gender, String mobileNumber, String emailAddress,
                           String municipality, String address, String image_url){

        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.location = municipality;
        this.address = address;
        this.image_url = image_url;

    }

    // Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getMunicipality() {
        return location;
    }

    public String getAddress() {
        return address;
    }
    public String getImage_url() {
        return image_url;
    }

    // setter


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setMunicipality(String municipality) {
        this.location = municipality;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
