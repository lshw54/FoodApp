package com.example.myfood.bean;

import java.io.Serializable;

public class Material implements Serializable {


    private String mname;
    private int type;
    private String amount;

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMname() {
        return mname;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

}
