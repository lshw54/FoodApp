package com.example.myfood.bean;

import java.io.Serializable;

public class Process implements Serializable {


    private String pcontent;
    private String pic;

    public void setPcontent(String pcontent) {
        this.pcontent = pcontent;
    }

    public String getPcontent() {
        return pcontent;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }

}
