package com.popogonry.notid.reply.replyRepository;

import com.popogonry.notid.reply.Reply;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryReplyRepository implements ReplyRepository {

    // HashMap<ReplyId, Reply>
    private static final HashMap<Long, Reply> replyHashMap = new HashMap<>();
    // Hash<NoticeId, HashSet<ReplyId>>
    private static final HashMap<Long, HashSet<Long>> noticeReplyHashMap= new HashMap<>();


    @Override
    public void addReplyData(Reply reply) {

    }

    @Override
    public Reply getReplyData(long replyId) {
        return null;
    }

    @Override
    public boolean hasReplyData(long replyId) {
        return false;
    }

    @Override
    public void removeReplyData(long replyId) {

    }

    @Override
    public void addNoticeReplyData(long noticeId, long replyId) {

    }

    @Override
    public boolean hasNoticeReplyData(long noticeId, long replyId) {
        return false;
    }

    @Override
    public void removeNoticeReplyData(long noticeId, long replyId) {

    }

    @Override
    public void addNoticeReplySetData(long noticeId, HashSet<Long> replySet) {

    }

    @Override
    public HashSet<Long> getNoticeReplySetData(long noticeId) {
        return null;
    }

    @Override
    public boolean hasNoticeReplySetData(long noticeId) {
        return false;
    }

    @Override
    public void removeNoticeReplySetData(long noticeId) {

    }

    @Override
    public void clearAll() {

    }
}
