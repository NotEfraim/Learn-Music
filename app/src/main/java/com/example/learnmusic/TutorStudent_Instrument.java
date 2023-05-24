package com.example.learnmusic;

public class TutorStudent_Instrument {

    private String instrument;
    private String proficiency;

    public TutorStudent_Instrument(String instrument, String proficiency) {
        this.instrument = instrument;
        this.proficiency = proficiency;
    }

    //Getter
    public String getInstrument() {
        return instrument;
    }

    public String getProficiency() {
        return proficiency;
    }

    //Setter
    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

}
