package notid.notice.repository;

import notid.channel.Channel;
import notid.notice.Notice;

import java.util.HashSet;

public interface NoticeRepository {
    void addNoticeData(Notice notice);
    Notice getNoticeData(long noticeId);
    boolean hasNoticeData(long noticeId);
    void removeNoticeData(long noticeId);

    void addChannelNoticeData(String channelName, long noticeId);
    boolean hasChannelNoticeData(String channelName, long noticeId);
    void removeChannelNoticeData(String channelName, long noticeId);

    void addChannelNoticeSetData(String channelName, HashSet<Long> noticeSet);
    HashSet<Long> getChannelNoticeSetData(String channelName);
    boolean hasChannelNoticeSetData(String channelName);
    void removeChannelNoticeSetData(String channelName);
}
