package com.popogonry.notid.notice.repository;

import com.popogonry.notid.notice.Notice;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryNoticeRepository implements NoticeRepository {

    // HashMap<NoticeId, Notice>
    private static final HashMap<Long, Notice> noticeHashMap = new HashMap<>();

    // HashMap<ChannelName, NoticeId>
    private static final HashMap<String, HashSet<Long>> channelNoticeHashMap= new HashMap<>();


    @Override
    public void addNoticeData(Notice notice) {
        noticeHashMap.put(notice.getId(), notice);
    }

    @Override
    public Notice getNoticeData(long noticeId) {
        return noticeHashMap.get(noticeId);
    }

    @Override
    public boolean hasNoticeData(long noticeId) {
        return noticeHashMap.containsKey(noticeId);
    }

    @Override
    public void removeNoticeData(long noticeId) {
        noticeHashMap.remove(noticeId);
    }

    @Override
    public void addChannelNoticeData(String channelName, long noticeId) {
        HashSet<Long> set = channelNoticeHashMap.getOrDefault(channelName, new HashSet<>());
        set.add(noticeId);
        channelNoticeHashMap.put(channelName, set);
    }

    @Override
    public boolean hasChannelNoticeData(String channelName, long noticeId) {
        return channelNoticeHashMap.getOrDefault(channelName, new HashSet<>()).contains(noticeId);
    }

    @Override
    public void removeChannelNoticeData(String channelName, long noticeId) {
        channelNoticeHashMap.getOrDefault(channelName, new HashSet<>()).remove(noticeId);
    }

    @Override
    public void addChannelNoticeSetData(String channelName, HashSet<Long> noticeSet) {
        channelNoticeHashMap.put(channelName, noticeSet);
    }

    @Override
    public HashSet<Long> getChannelNoticeSetData(String channelName) {
        return channelNoticeHashMap.getOrDefault(channelName, new HashSet<>());
    }

    @Override
    public boolean hasChannelNoticeSetData(String channelName) {
        return channelNoticeHashMap.containsKey(channelName);
    }

    @Override
    public void removeChannelNoticeSetData(String channelName) {
        channelNoticeHashMap.remove(channelName);
    }

    @Override
    public void clearAll() {
        noticeHashMap.clear();
        channelNoticeHashMap.clear();
    }
}
