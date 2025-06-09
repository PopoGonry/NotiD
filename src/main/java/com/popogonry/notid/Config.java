package com.popogonry.notid;

import com.popogonry.notid.channel.ChannelService;
import com.popogonry.notid.channel.repository.ChannelRepository;
import com.popogonry.notid.channel.repository.MemoryChannelRepository;
import com.popogonry.notid.cli.*;
import com.popogonry.notid.notice.NoticeService;
import com.popogonry.notid.notice.repository.MemoryNoticeRepository;
import com.popogonry.notid.notice.repository.NoticeRepository;
import com.popogonry.notid.reply.ReplyService;
import com.popogonry.notid.reply.replyRepository.MemoryReplyRepository;
import com.popogonry.notid.reply.replyRepository.ReplyRepository;
import com.popogonry.notid.user.repository.MemoryUserRepository;
import com.popogonry.notid.user.repository.UserRepositoy;
import com.popogonry.notid.user.UserService;

import java.util.HashMap;
import java.util.HashSet;

public class Config {

    public UserService userService() {
        return new UserService(userRepositoy());
    }

    public UserRepositoy userRepositoy() {
        return new MemoryUserRepository();
    }

    public ChannelService channelService() {
        return new ChannelService(channelRepository());
    }

    public ChannelRepository channelRepository() {
        return new MemoryChannelRepository();
    }

    public NoticeRepository noticeRepository() { return new MemoryNoticeRepository(); }

    public NoticeService noticeService() { return new NoticeService(noticeRepository(), channelRepository()); }

    public ReplyRepository replyRepository() { return new MemoryReplyRepository(); }

    public ReplyService replyService() { return new ReplyService(replyRepository(), noticeRepository(), userRepositoy()); }

    public Authentication authentication() {return new AuthenticationImpl(userRepositoy(), mainView());}

    public MainView mainView() {return new MainViewImpl(userRepositoy(), channelRepository(), noticeRepository(), new NoticeViewImpl(), channelView());}

    public ChannelView channelView() {
        return new ChannelViewImpl(mainView());
    }
}
