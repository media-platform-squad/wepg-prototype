package media.wepg.prototype.es.controller;

import media.wepg.prototype.es.model.Channel;
import media.wepg.prototype.es.repository.EsChannelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getDocumentById(@RequestParam("id") Long id) throws IOException {
        Optional<Channel> documentById = esChannelQuery.getDocumentById(id);

        return documentById
                .map(channel -> ResponseEntity.ok().body(channel.getChannelName()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/updateAllChannels")
    public ResponseEntity updateAllChannels() throws IOException {
        esChannelQuery.fetchAndIndexChannelData();

        return ResponseEntity.ok()
                .body("All programs were updated");
    }
}
