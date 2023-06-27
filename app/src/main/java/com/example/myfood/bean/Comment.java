package com.example.myfood.bean;

import java.io.Serializable;

public class Comment implements Serializable {
    private String commentContent;
    private int id;
    private int pid;
    private String userNid;
    private String time;
    private String userName;
    private String replyName;

    public Comment() {
    }

    public Comment(String commentContent, int id, int pid, String userNid, String time, String userName, String replyName) {
        this.commentContent = commentContent;
        this.id = id;
        this.pid = pid;
        this.userNid = userNid;
        this.time = time;
        this.userName = userName;
        this.replyName = replyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserNid() {
        return userNid;
    }

    public void setUserNid(String userNid) {
        this.userNid = userNid;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
