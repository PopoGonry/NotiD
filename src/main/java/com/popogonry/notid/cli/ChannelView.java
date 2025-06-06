package com.popogonry.notid.cli;

public interface ChannelView {

    void channelViewMain();
    void joinChannel();
    void channelToAdmin();
    void channelToManager();
    void channelToNormal();

    void channelInfo();

    void noticeList();

    void userReplyList();

    void updateChannel();

    void createNotice();

    void manageUser();
}
