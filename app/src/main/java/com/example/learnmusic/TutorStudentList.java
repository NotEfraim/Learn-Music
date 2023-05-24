package com.example.learnmusic;

public class TutorStudentList {

    private String id;
    private String name;
    private String age;
    private String gender;
    private String mobileNumber;
    private String address;
    private String emailAddress;
    private String image_url;

    public TutorStudentList(String id, String name, String age, String gender, String mobileNumber, String address, String emailAddress, String image_url) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.image_url = image_url;
    }

    // getter

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

    public String getAddress() {
        return address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getImage_url() {
        return image_url;
    }

    // Setter


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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
