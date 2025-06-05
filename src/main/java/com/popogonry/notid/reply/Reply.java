package com.popogonry.notid.reply;

import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.user.User;

import java.io.File;
import java.util.List;

public class Reply {
    private long id;
    private String title;
    private String content;
    private List<File> file;
    private Notice notice;
    private User author;

    public Reply(long id, String title, String content, List<File> file, Notice notice, User author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.file = file;
        this.notice = notice;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", file=" + file +
                ", notice=" + notice +
                ", author=" + author +
                '}';
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

    public List<File> getFile() {
        return file;
    }

    public void setFile(List<File> file) {
        this.file = file;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
