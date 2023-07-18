package media.wepg.prototype.orig.service;

import media.wepg.prototype.orig.model.Channel;
import media.wepg.prototype.orig.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public List<Channel> getAllChannels(){
        return channelRepository.findAll();
    }

    public Optional<Channel> getChannelsByServiceId(Long id){
        return channelRepository.findById(id);
    }
}
