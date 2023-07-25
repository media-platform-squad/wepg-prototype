package media.wepg.prototype.orig.controller;

import lombok.RequiredArgsConstructor;
import media.wepg.prototype.orig.model.ChannelOrigin;
import media.wepg.prototype.orig.service.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orig/wepg/channel")
public class ChannelController {

    private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);

    private final ChannelService channelService;

    @GetMapping
    public ResponseEntity<List<ChannelOrigin>> getAllChannels() {
        List<ChannelOrigin> allChannelOrigins = channelService.getAllChannels();

        logger.info(allChannelOrigins.stream().map(ChannelOrigin::getChannelName).toString());

        return ResponseEntity.ok()
                .body(allChannelOrigins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChannelOrigin> getChannelsById(@PathVariable("id") Long id) {
        Optional<ChannelOrigin> channel = channelService.getChannelsByServiceId(id);

        return channel
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}