package com.popogonry.notid.notice;

import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelJoinType;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.channel.repository.MemoryChannelRepository;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.notice.repository.MemoryNoticeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryNoticeRepositoryTest {

    private MemoryNoticeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryNoticeRepository();
        repository.clearAll();
    }

    @Test
    void addAndGetNotice() {
        Notice notice = createSampleNotice(1L, "공지1", "내용1", "디지털보안학과");

        repository.addNoticeData(notice);

        Notice found = repository.getNoticeData(1L);
        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("공지1");
        assertThat(found.getContent()).isEqualTo("내용1");
        assertThat(repository.getNoticeData(2L)).isNull();

    }

    @Test
    void hasNoticeData() {
        Notice notice = createSampleNotice(1L, "공지1", "내용1", "인공지능소프트웨어확과");

        assertThat(repository.hasNoticeData(1L)).isFalse();
        repository.addNoticeData(notice);
        assertThat(repository.hasNoticeData(1L)).isTrue();
    }

    @Test
    void removeNoticeData() {
        Notice notice = createSampleNotice(1L, "공지1", "내용1", "빅데이터학과");

        repository.addNoticeData(notice);
        assertThat(repository.hasNoticeData(1L)).isTrue();

        repository.removeNoticeData(1L);
        assertThat(repository.hasNoticeData(1L)).isFalse();
    }

    @Test
    void channelNoticeMappingTest() {
        String channel = "디지털보안학과";
        long noticeId = 100L;
        repository.addChannelNoticeSetData(channel, new HashSet<>());

        assertThat(repository.hasChannelNoticeSetData(channel)).isTrue();

        repository.addChannelNoticeData(channel, noticeId);
        assertThat(repository.hasChannelNoticeData(channel, noticeId)).isTrue();

        repository.removeChannelNoticeData(channel, noticeId);
        assertThat(repository.hasChannelNoticeData(channel, noticeId)).isFalse();

        repository.removeChannelNoticeSetData(channel);
        assertThat(repository.hasChannelNoticeSetData(channel)).isFalse();
    }

    @Test
    void getChannelNoticeSetData() {
        String channel = "디지털보안학과";
        HashSet<Long> noticeSet = new HashSet<>(Arrays.asList(1L, 2L, 3L));
        repository.addChannelNoticeSetData(channel, noticeSet);

        HashSet<Long> result = repository.getChannelNoticeSetData(channel);
        assertThat(result).containsExactlyInAnyOrder(1L, 2L, 3L);
    }

    private Notice createSampleNotice(long id, String title, String content, String channelName) {
        return new Notice(
                id,
                title,
                content,
                true,
                ChannelUserGrade.MANAGER,
                new Date(),
                new Date(),
                List.of(new File("dummy.txt")),
                new Channel(channelName, "des", ChannelJoinType.FREE)
        );
    }


}
