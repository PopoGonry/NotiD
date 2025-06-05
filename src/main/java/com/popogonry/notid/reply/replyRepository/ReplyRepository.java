package com.popogonry.notid.reply.replyRepository;

import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.reply.Reply;

import java.util.HashSet;

public interface ReplyRepository {
    void addReplyData(Reply reply);
    Reply getReplyData(long replyId);
    boolean hasReplyData(long replyId);
    void removeReplyData(long replyId);

    void addNoticeReplyData(long noticeId, long replyId);
    boolean hasNoticeReplyData(long noticeId, long replyId);
    void removeNoticeReplyData(long noticeId, long replyId);

    void addNoticeReplySetData(long noticeId, HashSet<Long> replySet);
    HashSet<Long> getNoticeReplySetData(long noticeId);
    boolean hasNoticeReplySetData(long noticeId);
    void removeNoticeReplySetData(long noticeId);

    void clearAll();
}
