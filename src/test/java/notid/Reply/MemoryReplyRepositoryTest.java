package notid.Reply;

import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelJoinType;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.notice.Notice;
import com.popogonry.notid.reply.Reply;
import com.popogonry.notid.reply.replyRepository.MemoryReplyRepository;
import com.popogonry.notid.user.User;
import com.popogonry.notid.user.UserGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryReplyRepositoryTest {
    private MemoryReplyRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryReplyRepository();
        repository.clearAll();
    }

    @Test
    void addAndGetReply() {
        Notice notice = new Notice(1L, "title", "content", true, ChannelUserGrade.MANAGER,
                new Date(), new Date(), List.of(new File("dummy.txt")),
                new Channel("channelName", "des", ChannelJoinType.FREE)
        );
        User user = new User("id", "password", "name", new Date(), "010-0000-0000", UserGrade.NORMAL);

        Reply reply = new Reply(1L, "title", "content", List.of(new File("dummy.txt")), notice, user);

        repository.addReplyData(reply);

        Reply found = repository.getReplyData(reply.getId());

        assertThat(found).isNotNull();
        assertThat(found).isEqualTo(reply);
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getTitle()).isEqualTo("title");

        assertThat(found.getAuthor()).isEqualTo(user);
        assertThat(found.getNotice()).isEqualTo(notice);

        assertThat(repository.getReplyData(2L)).isNull();
    }

    @Test
    void hasReplyData() {
        Notice notice = new Notice(1L, "title", "content", true, ChannelUserGrade.MANAGER,
                new Date(), new Date(), List.of(new File("dummy.txt")),
                new Channel("channelName", "des", ChannelJoinType.FREE)
        );
        User user = new User("id", "password", "name", new Date(), "010-0000-0000", UserGrade.NORMAL);

        Reply reply = new Reply(1L, "title", "content", List.of(new File("dummy.txt")), notice, user);


        assertThat(repository.hasReplyData(1L)).isFalse();
        repository.addReplyData(reply);
        assertThat(repository.hasReplyData(1L)).isTrue();
    }

    @Test
    void removeReplyData() {
        Notice notice = new Notice(1L, "title", "content", true, ChannelUserGrade.MANAGER,
                new Date(), new Date(), List.of(new File("dummy.txt")),
                new Channel("channelName", "des", ChannelJoinType.FREE)
        );
        User user = new User("id", "password", "name", new Date(), "010-0000-0000", UserGrade.NORMAL);

        Reply reply = new Reply(1L, "title", "content", List.of(new File("dummy.txt")), notice, user);

        repository.addReplyData(reply);

        assertThat(repository.hasReplyData(1L)).isTrue();

        repository.removeReplyData(1L);

        assertThat(repository.hasReplyData(1L)).isFalse();
    }

    @Test
    void addAndHasNoticeReplyData() {
        assertThat(repository.hasNoticeReplyData(1L, 10L)).isFalse();

        repository.addNoticeReplyData(1L, 10L);

        assertThat(repository.hasNoticeReplyData(1L, 10L)).isTrue();
    }


    @Test
    void removeNoticeReplyData() {
        repository.addNoticeReplyData(1L, 10L);
        assertThat(repository.hasNoticeReplyData(1L, 10L)).isTrue();

        repository.removeNoticeReplyData(1L, 10L);

        assertThat(repository.hasNoticeReplyData(1L, 10L)).isFalse();
    }

}
