package notid.channel;

import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelJoinType;
import com.popogonry.notid.channel.ChannelUserGrade;
import com.popogonry.notid.channel.repository.MemoryChannelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryChannelRepositoryTest {

    private MemoryChannelRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MemoryChannelRepository();
        repository.clearAll();
    }

    @Test
    void addAndGetChannel() {
        Channel channel = new Channel("공지방", "공지사항", "디지털보안학과", ChannelJoinType.ACCEPT);

        repository.addChannelData(channel);
        Channel found = repository.getChannelData("공지방");

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("공지방");
        assertThat(found.getDescription()).isEqualTo("공지사항");
        assertThat(found.getAffiliation()).isEqualTo("디지털보안학과");
        assertThat(found.getJoinType()).isEqualTo(ChannelJoinType.ACCEPT);
    }

    @Test
    void hasChannel() {
        repository.addChannelData(new Channel("채널A", "테스트", ChannelJoinType.FREE));

        assertThat(repository.hasChannelData("채널A")).isTrue();
        assertThat(repository.hasChannelData("없는채널")).isFalse();
    }

    @Test
    void removeChannel() {
        repository.addChannelData(new Channel("삭제채널", "삭제 테스트", ChannelJoinType.FREE));
        repository.removeChannelData("삭제채널");

        assertThat(repository.hasChannelData("삭제채널")).isFalse();
        assertThat(repository.getChannelData("삭제채널")).isNull();
    }

    @Test
    void addAndGetUserChannelSet() {
        HashSet<String> set = new HashSet<>();
        set.add("공지");
        set.add("자료");

        repository.addUserChannelSetData("user", set);

        assertThat(repository.hasUserChannelSetData("user")).isTrue();
        assertThat(repository.getUserChannelSetData("user")).containsExactlyInAnyOrder("공지", "자료");
    }

    @Test
    void addAndRemoveUserChannel() {
        repository.addUserChannelSetData("user", new HashSet<>());
        repository.addUserChannelData("user", "채널X");

        assertThat(repository.hasUserChannelData("user", "채널X")).isTrue();

        repository.removeUserChannelData("user", "채널X");

        assertThat(repository.hasUserChannelData("user", "채널X")).isFalse();
    }

    @Test
    void removeUserChannelSet() {
        repository.addUserChannelSetData("user", new HashSet<>());

        assertThat(repository.hasUserChannelSetData("user")).isTrue();

        repository.removeUserChannelSetData("user");

        assertThat(repository.hasUserChannelSetData("user")).isFalse();
        assertThat(repository.getUserChannelSetData("user")).isNotNull();
    }

    @Test
    void channelUserGradeMap_startsEmpty() {
        Channel channel = new Channel("채널등급", "등급 확인", ChannelJoinType.FREE);

        repository.addChannelData(channel);

        Channel found = repository.getChannelData("채널등급");
        assertThat(found.getChannelUserGradeHashMap()).isEmpty();
    }

    @Test
    void channelUserGradeMap_addEntry() {
        Channel channel = new Channel("등급테스트", "등급 추가", ChannelJoinType.FREE);
        channel.getChannelUserGradeHashMap().put("user", ChannelUserGrade.MANAGER);

        repository.addChannelData(channel);
        Channel found = repository.getChannelData("등급테스트");

        assertThat(found.getChannelUserGradeHashMap()).containsEntry("user", ChannelUserGrade.MANAGER);
    }
}
