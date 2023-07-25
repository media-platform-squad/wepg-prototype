package media.wepg.prototype.es.service;

import lombok.RequiredArgsConstructor;
import media.wepg.prototype.es.model.Channel;
import media.wepg.prototype.es.repository.EsChannelQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EsChannelService {

    private final EsChannelQuery esChannelQuery;

    public Optional<Channel> getChannelById(Long id) throws IOException {
        return esChannelQuery.getDocumentById(id);
    }

    public void updateChannelData() throws IOException {
        esChannelQuery.fetchAndIndexChannelData();
    }
}
