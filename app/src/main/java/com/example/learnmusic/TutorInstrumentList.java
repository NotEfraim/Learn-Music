package com.example.learnmusic;

public class TutorInstrumentList {

    // ADAPTER

    private String Id;
    private String Piano;
    private String Drums;
    private String Ukelele;
    private String Bass;
    private String Electric;
    private String Acoustic;
    private String Proficiency;
    private String Mon, Tue, Wed, Thu, Fri;
    private String Fee;
    private String time;

    public TutorInstrumentList(String id, String piano, String drums, String ukelele, String bass, String electric,
                               String acoustic, String proficiency, String mon, String tue, String wed,
                               String thu, String fri, String fee, String time) {

        this.Id = id;
        this.Piano = piano;
        this.Drums = drums;
        this.Ukelele = ukelele;
        this.Bass = bass;
        this.Electric = electric;
        this.Acoustic = acoustic;
        this.Proficiency = proficiency;
        this.Mon = mon;
        this.Tue = tue;
        this.Wed = wed;
        this.Thu = thu;
        this.Fri = fri;
        this.Fee = fee;
        this.time = time;

    }

    // Getter
    public String getId() {
        return Id;
    }

    public String getPiano() {
        return Piano;
    }

    public String getDrums() {
        return Drums;
    }

    public String getUkelele() {
        return Ukelele;
    }

    public String getBass() {
        return Bass;
    }

    public String getElectric() {
        return Electric;
    }

    public String getAcoustic() {
        return Acoustic;
    }

    public String getProficiency() {
        return Proficiency;
    }

    public String getMon() {
        return Mon;
    }

    public String getTue() {
        return Tue;
    }

    public String getWed() {
        return Wed;
    }

    public String getThu() {
        return Thu;
    }

    public String getFri() {
        return Fri;
    }

    public String getFee(){
        return Fee;
    }

    public String getTime() {
        return time;
    }


     // Setter

    public void setId(String id) {
        Id = id;
    }

    public void setPiano(String piano) {
        Piano = piano;
    }

    public void setDrums(String drums) {
        Drums = drums;
    }

    public void setUkelele(String ukelele) {
        Ukelele = ukelele;
    }

    public void setBass(String bass) {
        Bass = bass;
    }

    public void setElectric(String electric) {
        Electric = electric;
    }

    public void setAcoustic(String acoustic) {
        Acoustic = acoustic;
    }

    public void setProficiency(String proficiency) {
        Proficiency = proficiency;
    }

    public void setMon(String mon) {
        Mon = mon;
    }

    public void setTue(String tue) {
        Tue = tue;
    }

    public void setWed(String wed) {
        Wed = wed;
    }

    public void setThu(String thu) {
        Thu = thu;
    }

    public void setFri(String fri) {
        Fri = fri;
    }

    public  void  setFee(String fee){
        Fee = fee;
    }

    public void setTime(String time) {
        this.time = time;
    }



}
