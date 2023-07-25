package media.wepg.prototype.es.controller;

import media.wepg.prototype.es.controller.response.common.ApiResponse;
import media.wepg.prototype.es.model.Channel;
import media.wepg.prototype.es.model.dto.response.ChannelResponseDto;
import media.wepg.prototype.es.repository.EsChannelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/wepg/channel")
public class EsChannelController {

    private final EsChannelQuery esChannelQuery;

    @Autowired
    public EsChannelController(EsChannelQuery esChannelQuery) {
        this.esChannelQuery = esChannelQuery;
    }


    @GetMapping("/getDocument")
    public ApiResponse<Object> getDocumentById(@RequestParam("id") Long id) throws IOException {
        Optional<Channel> channelById = esChannelQuery.getDocumentById(id);

        return channelById
                .map(ch ->ApiResponse.ok(new ChannelResponseDto(ch)))
                .orElseGet(ApiResponse::fail);
    }

    @PostMapping("/updateAllChannels")
    public ApiResponse<Object> updateAllChannels() {
        try {
            esChannelQuery.fetchAndIndexChannelData();
        } catch (IOException e) {
            return ApiResponse.fail(e.getMessage());
        }
        return ApiResponse.ok();
    }
}
