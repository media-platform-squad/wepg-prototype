package media.wepg.prototype.controller;

import lombok.RequiredArgsConstructor;
import media.wepg.prototype.controller.response.common.ApiResponse;
import media.wepg.prototype.model.es.Channel;
import media.wepg.prototype.service.EsChannelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wepg/channel")
public class EsChannelController {

    private final EsChannelService channelService;

    @GetMapping("/getChannel")
    public ResponseEntity<Object> getChannelById(@RequestParam("id") Long id) {
        Optional<Channel> channelById;

        try {
            channelById = channelService.getChannelById(id);
        } catch (IOException e) {
            return ApiResponse.fail(e.getMessage());
        }

        return channelById
                .map(ApiResponse::ok)
                .orElseGet(ApiResponse::notFound);
    }

    @PostMapping("/updateAllChannels")
    public ResponseEntity<Object> updateAllChannels() {
        try {
            channelService.updateChannelData();
        } catch (IOException e) {
            return ApiResponse.fail(e.getMessage());
        }

        return ApiResponse.created();
    }
}
