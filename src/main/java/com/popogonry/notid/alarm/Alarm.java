package com.popogonry.notid.alarm;

import com.popogonry.notid.user.User;

import java.util.Date;

public class Alarm {

    private long id;
    private User user;
    private String content;
    private Date sendDate;

    public Alarm(long id, User user, String content, Date sendDate) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.sendDate = sendDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }
}
