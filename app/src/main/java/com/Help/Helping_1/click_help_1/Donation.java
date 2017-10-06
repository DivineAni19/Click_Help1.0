package com.Help.Helping_1.click_help_1;



public class Donation {

    private String event_hm;
    private String who;
    private String where;
    private String tk;
    private String date;

    public Donation() {
    }

    public Donation(String event_hm, String who, String where, String tk, String date) {
        this.event_hm = event_hm;
        this.who = who;
        this.where = where;
        this.tk = tk;
        this.date = date;
    }

    public String getEvent_hm() {
        return event_hm;
    }

    public void setEvent_hm(String event_hm) {
        this.event_hm = event_hm;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
