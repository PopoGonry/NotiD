package com.popogonry.notid.cli;

public interface MainView {

    void mainViewMain(String id);

    void checkAlarm();
    void userChannelList();
    void userNoticeList();
    void searchChannel();
    void createChannel();

}
