package com.popogonry.notid.Reply;

import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.notice.repository.MemoryNoticeRepository;
import com.popogonry.notid.reply.Reply;
import com.popogonry.notid.reply.ReplyService;
import com.popogonry.notid.reply.replyRepository.MemoryReplyRepository;
import com.popogonry.notid.user.User;
import com.popogonry.notid.user.UserGrade;
import com.popogonry.notid.user.repository.MemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReplyServiceTest {
    private MemoryReplyRepository replyRepository;
    private MemoryNoticeRepository noticeRepository;
    private MemoryUserRepository userRepository;
    private ReplyService replyService;

    @BeforeEach
    void setUp() {
        replyRepository = new MemoryReplyRepository();
        noticeRepository = new MemoryNoticeRepository();
        userRepository = new MemoryUserRepository();
        replyService = new ReplyService(replyRepository, noticeRepository, userRepository);

        // 초기화
        replyRepository.clearAll();
        noticeRepository.clearAll();
        userRepository.clear();
        replyService.resetCounter();

        // 테스트용 유저/공지 등록
        User user = new User("user1", "pw", "이름", new Date(), "01012345678", UserGrade.NORMAL);
        userRepository.addUserData(user);

        Notice notice = new Notice(1L, "공지제목", "공지내용", true, null, new Date(), new Date(), List.of(), null);
        noticeRepository.addNoticeData(notice);
    }

    @Test
    void createReply_success() {
        boolean result = replyService.createReply("제목", "내용", List.of(), 1L, "user1")!= 0L;
        assertThat(result).isTrue();
        assertThat(replyRepository.hasReplyData(1L)).isTrue();
        assertThat(replyRepository.hasNoticeReplyData(1L, 1L)).isTrue();
    }

    @Test
    void createReply_fail_whenUserOrNoticeMissing() {
        boolean result1 = replyService.createReply("제목", "내용", List.of(), 999L, "user1") != 0L; // 잘못된 공지
        boolean result2 = replyService.createReply("제목", "내용", List.of(), 1L, "noUser")!= 0L; // 잘못된 유저
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();
    }

    @Test
    void updateReply_success() {
        replyService.createReply("제목", "내용", List.of(), 1L, "user1");

        Notice notice = noticeRepository.getNoticeData(1L);
        User user = userRepository.getUserData("user1");

        Reply newReply = new Reply(1L, "수정됨", "변경된 내용", List.of(), notice, user);
        boolean result = replyService.updateReply(1L, newReply);

        assertThat(result).isTrue();
        assertThat(replyRepository.getReplyData(1L).getTitle()).isEqualTo("수정됨");
    }

    @Test
    void updateReply_fail_whenIdMismatchOrUserMismatch() {
        replyService.createReply("제목", "내용", List.of(), 1L, "user1");

        Notice notice = noticeRepository.getNoticeData(1L);
        User wrongUser = new User("다른사람", "pw", "이름", new Date(), "01099999999", UserGrade.NORMAL);

        Reply badIdReply = new Reply(999L, "x", "x", List.of(), notice, userRepository.getUserData("user1"));
        Reply badUserReply = new Reply(1L, "x", "x", List.of(), notice, wrongUser);

        assertThat(replyService.updateReply(1L, badIdReply)).isFalse();
        assertThat(replyService.updateReply(1L, badUserReply)).isFalse();
    }

    @Test
    void deleteReply_success() {
        replyService.createReply("제목", "내용", List.of(), 1L, "user1");

        boolean result = replyService.deleteReply(1L);
        assertThat(result).isTrue();
        assertThat(replyRepository.hasReplyData(1L)).isFalse();
        assertThat(replyRepository.hasNoticeReplyData(1L, 1L)).isFalse();
    }

    @Test
    void deleteReply_fail_whenNotExists() {
        boolean result = replyService.deleteReply(999L);
        assertThat(result).isFalse();
    }
}
