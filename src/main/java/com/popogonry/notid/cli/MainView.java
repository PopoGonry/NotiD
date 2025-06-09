package com.popogonry.notid.cli;

import com.popogonry.notid.user.User;

public interface MainView {

    void mainViewMain(User user);

    void checkAlarm(User user);
    void userChannelList(User user);
    void userNoticeList(User user);
    void searchChannel(User user);
    void createChannel(User user);

}
