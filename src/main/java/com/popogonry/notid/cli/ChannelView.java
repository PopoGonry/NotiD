package com.popogonry.notid.cli;

import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.user.User;

public interface ChannelView {

    void channelViewMain(Channel channel, User user);
    void joinChannel();
    void channelToAdmin(Channel channel, User user);
    void channelToManager(Channel channel, User user);
    void channelToNormal(Channel channel, User user);

    void channelInfo(Channel channel);

    void noticeList(Channel channel);

    void userReplyList(Channel channel, User user);

    void updateChannel(Channel channel, User user);

    void createNotice(Channel channel, User user);

    void manageUser(Channel channel, User user);
}
