package notid.notice.repository;

import notid.notice.Notice;

import java.util.HashSet;

public class MemoryNoticeRepository implements NoticeRepository {

    @Override
    public void addNoticeData(Notice notice) {

    }

    @Override
    public Notice getNoticeData(long noticeId) {
        return null;
    }

    @Override
    public boolean hasNoticeData(long noticeId) {
        return false;
    }

    @Override
    public void removeNoticeData(long noticeId) {

    }

    @Override
    public void addChannelNoticeData(String channelName, long noticeId) {

    }

    @Override
    public boolean hasChannelNoticeData(String channelName, long noticeId) {
        return false;
    }

    @Override
    public void removeChannelNoticeData(String channelName, long noticeId) {

    }

    @Override
    public void addChannelNoticeSetData(String channelName, HashSet<Long> noticeSet) {

    }

    @Override
    public HashSet<Long> getChannelNoticeSetData(String channelName) {
        return null;
    }

    @Override
    public boolean hasChannelNoticeSetData(String channelName) {
        return false;
    }

    @Override
    public void removeChannelNoticeSetData(String channelName) {

    }
}
