package com.popogonry.notid.reply;

import com.popogonry.notid.channel.repository.ChannelRepository;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.notice.repository.NoticeRepository;
import com.popogonry.notid.reply.replyRepository.ReplyRepository;
import com.popogonry.notid.user.User;
import com.popogonry.notid.user.repository.UserRepositoy;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ReplyService {
    private final ReplyRepository replyRepository;
    private final NoticeRepository noticeRepository;
    private final UserRepositoy userRepositoy;
    private final AtomicLong counter = new AtomicLong(1);

    public ReplyService(ReplyRepository replyRepository, NoticeRepository noticeRepository, UserRepositoy userRepositoy) {
        this.replyRepository = replyRepository;
        this.noticeRepository = noticeRepository;
        this.userRepositoy = userRepositoy;
    }

    public boolean createReply(String title, String content, List<File> file, long noticeId, String authorId) {
        long newId = counter.getAndIncrement();
        // 이미 존재하는 id 일때,
        if(replyRepository.hasReplyData(newId)) return false;
        // noticeId의 Notice가 존재하지 않을 때,
        if(!noticeRepository.hasNoticeData(noticeId)) return false;
        // authorId의 User가 존재하지 않을 때,
        if(!userRepositoy.hasUserData(authorId)) return false;

        Reply reply = new Reply(newId, title, content, file, noticeRepository.getNoticeData(noticeId), userRepositoy.getUserData(authorId));

        replyRepository.addReplyData(reply);
        replyRepository.addNoticeReplyData(noticeId, newId);

        return true;
    }

    public boolean updateReply(long id, Reply newReply) {
        // 존재하지 않을 때,
        if(!replyRepository.hasReplyData(id)) return false;

        Reply updateReply = replyRepository.getReplyData(id);

        // 요청 id와 저장된 id가 같지 않을 때,
        if(newReply.getId() != id) return false;
        // newReply의 noticeId와 저장된 noticeId가 같지 않을 때,
        if(updateReply.getNotice().getId() != newReply.getNotice().getId()) return false;
        // newReply의 authorId와 저장된 authorId가 같지 않을 때,
        if(!updateReply.getAuthor().getId().equals(newReply.getAuthor().getId())) return false;

        replyRepository.removeReplyData(updateReply.getId());
        replyRepository.removeNoticeReplyData(updateReply.getNotice().getId(), updateReply.getId());

        replyRepository.addReplyData(newReply);
        replyRepository.addNoticeReplyData(newReply.getNotice().getId(), newReply.getId());

        return true;
    }

    public boolean deleteReply(long id) {
        // 존재하지 않을 때,
        if(!replyRepository.hasReplyData(id)) return false;

        Reply deleteReply = replyRepository.getReplyData(id);

        replyRepository.removeReplyData(id);
        replyRepository.removeNoticeReplyData(deleteReply.getNotice().getId(), deleteReply.getId());
        return true;
    }


}
