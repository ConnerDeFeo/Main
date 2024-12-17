package com.ufund.api.ufundapi.model;

public class NeedDatePair {
    
    private Need need;
    private String date;

    public NeedDatePair(Need need, String date) {
        this.need = need;
        this.date = date;
    }

    public Need getNeed() {
        return need;
    }

    public void setNeed(Need need) {
        this.need = need;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
