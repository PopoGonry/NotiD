package com.popogonry.notid.reply.replyRepository;

import com.popogonry.notid.reply.Reply;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryReplyRepository implements ReplyRepository {

    // HashMap<ReplyId, Reply>
    private static final HashMap<Long, Reply> replyHashMap = new HashMap<>();
    // Hash<NoticeId, HashSet<ReplyId>>
    private static final HashMap<Long, HashSet<Long>> noticeReplyHashMap = new HashMap<>();


    @Override
    public void addReplyData(Reply reply) {
        replyHashMap.put(reply.getId(), reply);
    }

    @Override
    public Reply getReplyData(long replyId) {
        return replyHashMap.get(replyId);
    }

    @Override
    public boolean hasReplyData(long replyId) {
        return replyHashMap.containsKey(replyId);
    }

    @Override
    public void removeReplyData(long replyId) {
        replyHashMap.remove(replyId);
    }

    @Override
    public void addNoticeReplyData(long noticeId, long replyId) {
        if(!noticeReplyHashMap.containsKey(replyId)) {
            noticeReplyHashMap.put(replyId, new HashSet<>());
        }
        noticeReplyHashMap.get(replyId).add(noticeId);
    }

    @Override
    public boolean hasNoticeReplyData(long noticeId, long replyId) {
        return noticeReplyHashMap.get(replyId).contains(noticeId);
    }

    @Override
    public void removeNoticeReplyData(long noticeId, long replyId) {
        noticeReplyHashMap.get(replyId).remove(noticeId);
    }

    @Override
    public void addNoticeReplySetData(long noticeId, HashSet<Long> replySet) {
        noticeReplyHashMap.put(noticeId, replySet);
    }

    @Override
    public HashSet<Long> getNoticeReplySetData(long noticeId) {
        return noticeReplyHashMap.get(noticeId);
    }

    @Override
    public boolean hasNoticeReplySetData(long noticeId) {
        return noticeReplyHashMap.containsKey(noticeId);
    }

    @Override
    public void removeNoticeReplySetData(long noticeId) {
        noticeReplyHashMap.remove(noticeId);
    }

    @Override
    public void clearAll() {
        replyHashMap.clear();
        noticeReplyHashMap.clear();
    }
}
