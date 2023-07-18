package media.wepg.prototype.orig.controller;

import jakarta.transaction.Transactional;
import media.wepg.prototype.model.Channel;
import media.wepg.prototype.service.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/api/orig/wepg/channel")
public class ChannelController {

    private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);

    private final ChannelService channelService;

    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping
    public ResponseEntity<List<Channel>> getAllChannels(){
        List<Channel> allChannels = channelService.getAllChannels();

        logger.info(allChannels.stream().map(channel -> channel.getChannelName()).toString());

        return ResponseEntity.ok()
                .body(allChannels);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Channel>> getChannelsById(@PathVariable Long id){
        Optional<Channel> channel = channelService.getChannelsByServiceId(id);

        return ResponseEntity.ok()
                .body(channel);
    }
}
