package com.example.myfood.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CircleListBean implements Serializable {
    private int id;
    private String content;
    private String account;
    private String lat;
    private String lon;
    private String location;
    private String imgs;
    private String likes;
    private String likeNames;
    private String time;

    private User user;
    private List<Comment> commentList;




    public CircleListBean() {
    }

    public CircleListBean(int id, String content, String account,
                          String lat, String lon, String location,
                          String imgs, String likes,String likeNames,String time) {
        this.id = id;
        this.content = content;
        this.account = account;
        this.lat = lat;
        this.lon = lon;
        this.location = location;
        this.imgs = imgs;
        this.likes = likes;
        this.time = time;
        this.likeNames = likeNames;

    }


    public List<Comment> getCommentList() {
        if (commentList == null)
            commentList = new ArrayList<>();
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getLikeNames() {
        return likeNames;
    }

    public void setLikeNames(String likeNames) {
        this.likeNames = likeNames;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }



}
