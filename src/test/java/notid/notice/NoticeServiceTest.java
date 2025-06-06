package notid.notice;

import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelJoinType;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.channel.repository.ChannelRepository;
import com.popogonry.notid.channel.repository.MemoryChannelRepository;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.notice.NoticeService;
import com.popogonry.notid.notice.repository.MemoryNoticeRepository;
import com.popogonry.notid.user.UserGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NoticeServiceTest {
    private NoticeService noticeService;
    private MemoryNoticeRepository noticeRepository;
    private ChannelRepository channelRepository;

    @BeforeEach
    void setUp() {
        noticeRepository = new MemoryNoticeRepository();
        channelRepository = new MemoryChannelRepository();
        noticeRepository.clearAll();
        channelRepository.clearAll();
        noticeService = new NoticeService(noticeRepository, channelRepository);
    }

    @Test
    void createNotice_success() {
        channelRepository.addChannelData(new Channel("디지털보안학과", "설명", ChannelJoinType.ACCEPT));

        boolean result = noticeService.createNotice(
                "공지 제목",
                "공지 내용",
                true,
                ChannelUserGrade.MANAGER,
                new Date(),
                new Date(),
                List.of(new File("file1.txt")),
                "디지털보안학과"
        );

        assertThat(result).isTrue();
        assertThat(noticeRepository.hasNoticeData(1L)).isTrue();
        assertThat(noticeRepository.hasChannelNoticeData("디지털보안학과", 1L)).isTrue();
    }

    @Test
    void createNotice_fail_when_existSameId() {
        noticeRepository.addNoticeData(new Notice(1L, "기존", "기존", true, ChannelUserGrade.NORMAL, new Date(), new Date(), null, new Channel("name", "des", ChannelJoinType.FREE)));

        boolean result = noticeService.createNotice(
                "공지 제목",
                "공지 내용",
                true,
                ChannelUserGrade.MANAGER,
                new Date(),
                new Date(),
                List.of(new File("file1.txt")),
                "디지털보안학과"
        );

        assertThat(result).isFalse();
    }

    @Test
    void updateNotice_success() {
        // 기존 공지 생성
        channelRepository.addChannelData(new Channel("디지털보안학과", "des", ChannelJoinType.FREE));
        noticeService.createNotice("공지 제목", "공지 내용", true, ChannelUserGrade.MANAGER, new Date(), new Date(), List.of(), "디지털보안학과");

        Notice updatedNotice = new Notice(
                1L,
                "수정된 제목",
                "수정된 내용",
                false,
                ChannelUserGrade.NORMAL,
                new Date(),
                new Date(),
                List.of(new File("new.txt")),
                new Channel("디지털보안학과", "des", ChannelJoinType.FREE)
        );

        boolean result = noticeService.updateNotice(1L, updatedNotice);

        assertThat(result).isTrue();
        Notice notice = noticeRepository.getNoticeData(1L);
        assertThat(notice.getTitle()).isEqualTo("수정된 제목");
        assertThat(notice.isReplyAllowed()).isFalse();
    }

    @Test
    void updateNotice_fail_when_wrongChannel() {

        channelRepository.addChannelData(new Channel("채널A", "des", ChannelJoinType.FREE));
        noticeService.createNotice("제목", "내용", true, ChannelUserGrade.ADMIN, new Date(), new Date(), List.of(), "채널A");

        Notice invalidUpdate = new Notice(
                1L,
                "다른채널",
                "내용",
                true,
                ChannelUserGrade.ADMIN,
                new Date(),
                new Date(),
                List.of(),
                new Channel("채널B", "des", ChannelJoinType.FREE)
        );

        boolean result = noticeService.updateNotice(1L, invalidUpdate);
        assertThat(result).isFalse();
    }
    @Test
    void updateNotice_fail_when_wrongId() {

        channelRepository.addChannelData(new Channel("디지털보안학과", "des", ChannelJoinType.FREE));
        noticeService.createNotice("제목", "내용", true, ChannelUserGrade.ADMIN, new Date(), new Date(), List.of(), "디지털보안학과");

        Notice invalidUpdate = new Notice(
                2L,
                "제목",
                "내용",
                true,
                ChannelUserGrade.ADMIN,
                new Date(),
                new Date(),
                List.of(),
                new Channel("디지털보안학과", "des", ChannelJoinType.FREE)
        );

        boolean result = noticeService.updateNotice(1L, invalidUpdate);
        assertThat(result).isFalse();
    }

    @Test
    void deleteNotice_success() {
        channelRepository.addChannelData(new Channel("디지털보안학과", "설명", ChannelJoinType.ACCEPT));

        noticeService.createNotice("삭제될 제목", "삭제될 내용", true, ChannelUserGrade.ADMIN, new Date(), new Date(), List.of(), "디지털보안학과");

        boolean result = noticeService.deleteNotice(1L);

        assertThat(result).isTrue();
        assertThat(noticeRepository.hasNoticeData(1L)).isFalse();
        assertThat(noticeRepository.hasChannelNoticeSetData("디지털보안학과")).isTrue();
        assertThat(noticeRepository.getChannelNoticeSetData("디지털보안학과")).doesNotContain(1L);
    }

    @Test
    void deleteNotice_fail_when_notExist() {
        boolean result = noticeService.deleteNotice(999L);
        assertThat(result).isFalse();
    }
}
