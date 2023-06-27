package com.example.myfood.bean;

import java.io.Serializable;
import java.util.List;

public class Food implements Serializable {


    private int id;
    private int classid;
    private String name;
    private String peoplenum;
    private String preparetime;
    private String cookingtime;
    private String content;
    private String pic;
    private String tag;
    private String type;
    private List<Material> material;
    private List<Process> process;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public int getClassid() {
        return classid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPeoplenum(String peoplenum) {
        this.peoplenum = peoplenum;
    }

    public String getPeoplenum() {
        return peoplenum;
    }

    public void setPreparetime(String preparetime) {
        this.preparetime = preparetime;
    }

    public String getPreparetime() {
        return preparetime;
    }

    public void setCookingtime(String cookingtime) {
        this.cookingtime = cookingtime;
    }

    public String getCookingtime() {
        return cookingtime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setMaterial(List<Material> material) {
        this.material = material;
    }

    public List<Material> getMaterial() {
        return material;
    }

    public void setProcess(List<Process> process) {
        this.process = process;
    }

    public List<Process> getProcess() {
        return process;
    }

}
