package com.popogonry.notid;

import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelJoinType;
import com.popogonry.notid.channel.ChannelService;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.channel.repository.ChannelRepository;
import com.popogonry.notid.cli.Authentication;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.notice.NoticeService;
import com.popogonry.notid.user.User;
import com.popogonry.notid.user.UserGrade;
import com.popogonry.notid.user.UserService;
import com.popogonry.notid.user.repository.UserRepositoy;

import javax.swing.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Config config = new Config();

        UserService userService = config.userService();
        userService.createUser("admin", "admin", "admin", new Date(), "010-3727-0770", UserGrade.MASTER);
        userService.createUser("zxc", "zxc", "zxc", new Date(), "010-3727-0770", UserGrade.NORMAL);

        UserRepositoy userRepositoy = config.userRepositoy();
        ChannelRepository channelRepository = config.channelRepository();

        ChannelService channelService = config.channelService();
        channelService.createChannel("channel1", "des", "", ChannelJoinType.FREE);
        channelService.createChannel("channel2", "des", "", ChannelJoinType.ACCEPT);

        User user = userRepositoy.getUserData("admin");
        Channel channel1 = channelRepository.getChannelData("channel1");
        Channel channel2 = channelRepository.getChannelData("channel2");
        channelService.joinChannel(user, channel1);
        channelService.joinChannel(user, channel2);
        channel1.addChannelUserGrade(user.getId(), ChannelUserGrade.ADMIN);
        channel2.addChannelUserGrade(user.getId(), ChannelUserGrade.ADMIN);

        NoticeService noticeService = config.noticeService();
        noticeService.createNotice("제목", "내용", false, ChannelUserGrade.NORMAL, new Date(), new Date(), null, channel1.getName());



        Authentication.authenticate();
    }
}