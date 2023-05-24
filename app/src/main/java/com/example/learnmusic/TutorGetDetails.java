package com.example.learnmusic;

public class TutorGetDetails {

    private String email;
    private String Instrument;
    private String Proficiency;
    private String Fee;
    private String sunday;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String time;


    public TutorGetDetails(String email, String instrument, String proficiency, String fee, String sunday, String monday,
                           String tuesday, String wednesday, String thursday, String friday, String saturday,
                           String time) {

        this.email = email;
        this.Instrument = instrument;
        this.Proficiency = proficiency;
        this.Fee = fee;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.time = time;

    }


    //getter
    public String getEmail() {
        return email;
    }

    public String getInstrument() {
        return Instrument;
    }

    public String getProficiency() {
        return Proficiency;
    }

    public String getFee() {
        return Fee;
    }

    public String getSunday() {
        return sunday;
    }

    public String getMonday() {
        return monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public String getFriday() {
        return friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public String getTime() {
        return time;
    }

    // setter

    public void setInstrument(String instrument) {
        Instrument = instrument;
    }

    public void setProficiency(String proficiency) {
        Proficiency = proficiency;
    }

    public void setFee(String fee) {
        Fee = fee;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
