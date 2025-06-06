package com.popogonry.notid.notice;

import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.channel.repository.ChannelRepository;
import com.popogonry.notid.notice.repository.NoticeRepository;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final ChannelRepository channelRepository;
    private final AtomicLong counter = new AtomicLong(1);


    public NoticeService(NoticeRepository noticeRepository, ChannelRepository channelRepository) {
        this.noticeRepository = noticeRepository;
        this.channelRepository = channelRepository;
    }

    public boolean createNotice(String title, String content, boolean isReplyAllowed, ChannelUserGrade userGrade, Date scheduledTime, Date replyDeadline, List<File> attachments, String channelName) {
        long newId = counter.getAndIncrement();
        // 똑같은 id가 존재할때,
        if (noticeRepository.hasNoticeData(newId)) return false;
        // channelName의 Channel이 존재하지 않을 때,
        if (!channelRepository.hasChannelData(channelName)) return false;

        Notice notice = new Notice(newId, title, content, isReplyAllowed, userGrade, scheduledTime, replyDeadline, attachments, channelRepository.getChannelData(channelName));
        noticeRepository.addNoticeData(notice);
        noticeRepository.addChannelNoticeData(channelName, newId);

        return true;
    }

    public boolean updateNotice(long id, Notice newNotice) {
        // 존재하지 않을때,
        if(!noticeRepository.hasNoticeData(id)) return false;

        Notice updateNotice = noticeRepository.getNoticeData(id);

        // 요청 id와 저장된 notice의 id가 같지 않을 때,
        if(newNotice.getId() != id) return false;

        // newNotice의 채널명과, 저장된 notice의 채널명이 다를때,
        if(!newNotice.getChannel().getName().equals(updateNotice.getChannel().getName())) return false;

        noticeRepository.removeNoticeData(updateNotice.getId());
        noticeRepository.removeChannelNoticeData(updateNotice.getChannel().getName(), updateNotice.getId());

        noticeRepository.addNoticeData(newNotice);
        noticeRepository.addChannelNoticeData(newNotice.getChannel().getName(), newNotice.getId());

        return true;
    }


    public boolean deleteNotice(long id) {

        // 존재하지 않을때,
        if(!noticeRepository.hasNoticeData(id)) return false;

        Notice deleteNotice = noticeRepository.getNoticeData(id);

        noticeRepository.removeNoticeData(id);
        noticeRepository.removeChannelNoticeData(deleteNotice.getChannel().getName(), deleteNotice.getId());

        return true;
    }
}
