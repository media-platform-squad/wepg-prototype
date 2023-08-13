package media.wepg.prototype.orig.repository;

import media.wepg.prototype.model.ChannelOrigin;
import media.wepg.prototype.repository.ChannelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureDataJpa
@Transactional
class ChannelOriginRepositoryTest {

    @Autowired
    private ChannelRepository channelRepository;


    @Test
    @DisplayName("전체 채널 개수가 689가 맞는지 확인한다. (07-19 기준)")
    void findAllBy() {
        List<ChannelOrigin> allChannelOrigins = channelRepository.findAll();

        assertThat(allChannelOrigins).hasSize(689);
    }

    @Test
    @DisplayName("채널 id로 해당 채널을 올바르게 가져오는지 확인한다. (11번)")
    void findAllById() {
        Optional<ChannelOrigin> channelById = channelRepository.findById(11L);

        channelById.ifPresent(channel -> assertThat(channel.getId()).isEqualTo(11L));
    }

    @Test
    @DisplayName("존재하지 않는 채널을 가져오는 경우 실패한다. (9999번)")
    void findAllyById_fail_none_existing_channel() {
        Optional<ChannelOrigin> channelById = channelRepository.findById(9999L);

        assertThat(channelById).isEmpty();
    }
}