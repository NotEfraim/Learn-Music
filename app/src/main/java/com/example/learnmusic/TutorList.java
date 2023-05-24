package com.example.learnmusic;

public class TutorList {

    // ADAPTER

    private String Name;
    private String Age;
    private String Gender;
    private String MobileNumber;
    private  String EmailAddress;
    private String address;
    private String id;

    public TutorList(String id, String name, String age, String gender, String mobileNumber, String email,
                     String address) {
       this.Name = name;
       this.Age = age;
       this.Gender = gender;
       this.MobileNumber = mobileNumber;
       this.EmailAddress = email;
       this.address = address;
       this.id = id;

    }
    // getter

    public String getId(){
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getAge() {
        return Age;
    }

    public String getGender() {
        return Gender;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getAddress(){
        return address;
    }



    // setter

    public void setName(String name) {
        Name = name;
    }

    public void setAge(String age) {
        Age = age;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public void setEmailAddress(String email) {
        EmailAddress = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(String id) {
        this.id = id;
    }



}
