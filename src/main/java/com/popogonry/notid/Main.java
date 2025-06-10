package com.popogonry.notid;

import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelJoinType;
import com.popogonry.notid.channel.ChannelService;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.channel.repository.ChannelRepository;
import com.popogonry.notid.cli.Authentication;
import com.popogonry.notid.user.User;
import com.popogonry.notid.user.UserGrade;
import com.popogonry.notid.user.UserService;
import com.popogonry.notid.user.repository.UserRepositoy;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Config config = new Config();

        UserService userService = config.userService();
        userService.createUser("admin", "admin", "admin", new Date(), "010-3727-0770", UserGrade.MASTER);

        UserRepositoy userRepositoy = config.userRepositoy();
        ChannelRepository channelRepository = config.channelRepository();

        ChannelService channelService = config.channelService();
        channelService.createChannel("channel", "des", "", ChannelJoinType.FREE);

        User user = userRepositoy.getUserData("admin");
        Channel channel = channelRepository.getChannelData("channel");
        channelService.joinChannel(user, channel);
        channel.addChannelUserGrade(user.getId(), ChannelUserGrade.ADMIN);




        Authentication.authenticate();
    }
}