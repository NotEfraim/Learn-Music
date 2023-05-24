package com.example.learnmusic;

public class TutorRequestList_instrument {

    // for Instrument profile of student
    private String id;
    private String student_id;
    private String tutor_email;
    private String instrument;
    private String proficiency;
    private String sunday, monday, tuesday, wednesday, thursday, friday, saturday;

    public TutorRequestList_instrument (String id,String student_id, String tutor_email, String instrument, String proficiency, String sunday,
                                        String monday, String tuesday, String wednesday, String thursday,
                                        String friday, String saturday){
        this.id = id;
        this.student_id = student_id;
        this.tutor_email = tutor_email;
        this.instrument = instrument;
        this.proficiency = proficiency;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday =saturday;


    }

    // Getter


    public String getId() {
        return id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public String getTutor_email() {
        return tutor_email;
    }

    public String getInstrument() {
        return instrument;
    }

    public String getSunday() {
        return sunday;
    }


    public String getProficiency() {
        return proficiency;
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

    // Setter



    public void setTutor_email(String tutor_email) {
        this.tutor_email = tutor_email;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
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
}
