package com.popogonry.notid.cli;

public interface MainView {

    void mainViewMain(String id);

    void checkAlarm(String id);
    void userChannelList(String id);
    void userNoticeList(String id);
    void searchChannel(String id);
    void createChannel(String id);

}
