package com.popogonry.notid;

import com.popogonry.notid.alarm.AlarmService;
import com.popogonry.notid.alarm.repository.AlarmRepository;
import com.popogonry.notid.alarm.repository.MemoryAlarmRepository;
import com.popogonry.notid.channel.ChannelService;
import com.popogonry.notid.channel.repository.ChannelRepository;
import com.popogonry.notid.channel.repository.MemoryChannelRepository;
import com.popogonry.notid.notice.NoticeService;
import com.popogonry.notid.notice.repository.MemoryNoticeRepository;
import com.popogonry.notid.notice.repository.NoticeRepository;
import com.popogonry.notid.reply.ReplyService;
import com.popogonry.notid.reply.replyRepository.MemoryReplyRepository;
import com.popogonry.notid.reply.replyRepository.ReplyRepository;
import com.popogonry.notid.user.repository.MemoryUserRepository;
import com.popogonry.notid.user.repository.UserRepositoy;
import com.popogonry.notid.user.UserService;


public class Config {

    public UserService userService() {
        return new UserService(userRepositoy());
    }

    public UserRepositoy userRepositoy() {
        return new MemoryUserRepository();
    }

    public ChannelService channelService() {
        return new ChannelService(channelRepository(), userRepositoy());
    }

    public ChannelRepository channelRepository() {
        return new MemoryChannelRepository();
    }

    public NoticeRepository noticeRepository() { return new MemoryNoticeRepository(); }

    public NoticeService noticeService() { return new NoticeService(noticeRepository(), channelRepository()); }

    public ReplyRepository replyRepository() { return new MemoryReplyRepository(); }

    public ReplyService replyService() { return new ReplyService(replyRepository(), noticeRepository(), userRepositoy()); }

    public AlarmRepository alarmRepository() { return new MemoryAlarmRepository(); }

    public AlarmService alarmService() { return new AlarmService(alarmRepository(), userRepositoy(), channelService());}
}
