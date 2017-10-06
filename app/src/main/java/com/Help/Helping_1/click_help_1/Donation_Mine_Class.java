package com.Help.Helping_1.click_help_1;



public class Donation_Mine_Class {

    String Event_name;
    String date;
    String tk;
    String payment;

    public Donation_Mine_Class() {
    }

    public Donation_Mine_Class(String event_name, String date, String tk, String payment) {
        Event_name = event_name;
        this.date = date;
        this.tk = tk;
        this.payment = payment;
    }

    public String getEvent_name() {
        return Event_name;
    }

    public void setEvent_name(String event_name) {
        Event_name = event_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
