package media.wepg.prototype.es.controller;

import lombok.RequiredArgsConstructor;
import media.wepg.prototype.es.controller.response.common.ApiResponse;
import media.wepg.prototype.es.model.Channel;
import media.wepg.prototype.es.service.EsChannelService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wepg/channel")
public class EsChannelController {

    private final EsChannelService channelService;

    @GetMapping("/getDocument")
    public ApiResponse<Object> getDocumentById(@RequestParam("id") Long id) {
        Optional<Channel> channelById;

        try {
            channelById = channelService.getChannelById(id);
        } catch (IOException e) {
            return ApiResponse.fail(e.getMessage());
        }

        return channelById
                .map(ApiResponse::ok)
                .orElseGet(ApiResponse::fail);
    }

    @PostMapping("/updateAllChannels")
    public ApiResponse<Object> updateAllChannels() {
        try {
            channelService.updateChannelData();
        } catch (IOException e) {
            return ApiResponse.fail(e.getMessage());
        }

        return ApiResponse.ok();
    }
}
