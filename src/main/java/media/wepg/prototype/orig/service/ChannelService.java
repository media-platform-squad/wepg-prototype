package media.wepg.prototype.orig.service;

import lombok.RequiredArgsConstructor;
import media.wepg.prototype.orig.model.ChannelOrigin;
import media.wepg.prototype.orig.repository.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;

    public List<ChannelOrigin> getAllChannels() {
        return channelRepository.findAll();
    }

    public Optional<ChannelOrigin> getChannelsByServiceId(Long id) {
        return channelRepository.findById(id);
    }
}
