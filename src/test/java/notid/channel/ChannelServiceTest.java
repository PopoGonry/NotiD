package notid.channel;

import com.popogonry.notid.channel.Channel;
import com.popogonry.notid.channel.ChannelJoinType;
import com.popogonry.notid.channel.ChannelService;
import com.popogonry.notid.channel.repository.ChannelRepository;
import com.popogonry.notid.channel.repository.MemoryChannelRepository;
import com.popogonry.notid.user.repository.MemoryUserRepository;
import com.popogonry.notid.user.repository.UserRepositoy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelServiceTest {

    private ChannelRepository channelRepository;
    private ChannelService channelService;

    @BeforeEach
    void setUp() {
        channelRepository = new MemoryChannelRepository();
        channelService = new ChannelService(channelRepository, new MemoryUserRepository());

        channelRepository.clearAll();
    }

    @Test
    void createChannel_success() {
        boolean result = channelService.createChannel("공지방", "학교 공지", "디지털보안학과", ChannelJoinType.ACCEPT);

        assertThat(result).isTrue();
        assertThat(channelRepository.hasChannelData("공지방")).isTrue();

        Channel saved = channelRepository.getChannelData("공지방");
        assertThat(saved.getAffiliation()).isEqualTo("디지털보안학과");
        assertThat(saved.getJoinType()).isEqualTo(ChannelJoinType.ACCEPT);
    }

    @Test
    void createChannel_fail_whenAlreadyExists() {
        channelService.createChannel("중복방", "처음 생성", "인공지능소프트웨어학과", ChannelJoinType.FREE);

        boolean result = channelService.createChannel("중복방", "두 번째 생성", "빅데이터학과", ChannelJoinType.ACCEPT);

        assertThat(result).isFalse();

        Channel channel = channelRepository.getChannelData("중복방");
        assertThat(channel.getDescription()).isEqualTo("처음 생성");
    }

    @Test
    void updateChannel_success() {
        channelService.createChannel("업데이트방", "초기 설명", "경영학과", ChannelJoinType.FREE);

        Channel updated = new Channel("업데이트방", "바뀐 설명", "경제학과", ChannelJoinType.ACCEPT);
        boolean result = channelService.updateChannel("업데이트방", updated);

        assertThat(result).isTrue();

        Channel channel = channelRepository.getChannelData("업데이트방");
        assertThat(channel.getDescription()).isEqualTo("바뀐 설명");
        assertThat(channel.getAffiliation()).isEqualTo("경제학과");
        assertThat(channel.getJoinType()).isEqualTo(ChannelJoinType.ACCEPT);
    }

    @Test
    void updateChannel_fail_whenNotExists() {
        Channel channel = new Channel("없는채널", "내용", "어딘가", ChannelJoinType.FREE);

        boolean result = channelService.updateChannel("없는채널", channel);

        assertThat(result).isFalse();
    }

    @Test
    void updateChannel_fail_whenNameMismatch() {
        channelService.createChannel("원래이름", "초기 설명", "전기제어학과", ChannelJoinType.FREE);

        Channel newChannel = new Channel("다른이름", "수정 시도", "전기제어학과", ChannelJoinType.FREE);

        boolean result = channelService.updateChannel("원래이름", newChannel);

        assertThat(result).isFalse();
    }

    @Test
    void deleteChannel_success() {
        channelService.createChannel("삭제방", "삭제 대상", "철학과", ChannelJoinType.FREE);

        boolean result = channelService.deleteChannel("삭제방");

        assertThat(result).isTrue();
        assertThat(channelRepository.hasChannelData("삭제방")).isFalse();
    }

    @Test
    void deleteChannel_fail_whenNotExists() {
        boolean result = channelService.deleteChannel("존재X");

        assertThat(result).isFalse();
    }
}
