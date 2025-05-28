package com.popogonry.notid.notice;

import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.user.UserGrade;

import java.io.File;
import java.util.Date;
import java.util.List;

public class Notice {
    private long id;
    private String title;
    private String content;
    private boolean isReplyAllowed;
    private ChannelUserGrade userGrade;
    private Date scheduledTime;
    private Date replyDeadline;
    private List<File> attachments;


    public Notice(long id, String title, String content, boolean isReplyAllowed, ChannelUserGrade userGrade, Date scheduledTime, Date replyDeadline, List<File> attachments, String channelName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isReplyAllowed = isReplyAllowed;
        this.userGrade = userGrade;
        this.scheduledTime = scheduledTime;
        this.replyDeadline = replyDeadline;
        this.attachments = attachments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isReplyAllowed() {
        return isReplyAllowed;
    }

    public void setReplyAllowed(boolean replyAllowed) {
        isReplyAllowed = replyAllowed;
    }

    public ChannelUserGrade getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(ChannelUserGrade userGrade) {
        this.userGrade = userGrade;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Date getReplyDeadline() {
        return replyDeadline;
    }

    public void setReplyDeadline(Date replyDeadline) {
        this.replyDeadline = replyDeadline;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<File> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isReplyAllowed=" + isReplyAllowed +
                ", userGrade=" + userGrade +
                ", scheduledTime=" + scheduledTime +
                ", replyDeadline=" + replyDeadline +
                ", attachments=" + attachments +
                '}';
    }
}
