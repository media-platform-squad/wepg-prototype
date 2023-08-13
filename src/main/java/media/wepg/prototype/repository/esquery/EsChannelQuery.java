package media.wepg.prototype.repository.esquery;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import lombok.extern.slf4j.Slf4j;
import media.wepg.prototype.model.ChannelOrigin;
import media.wepg.prototype.model.es.Channel;
import media.wepg.prototype.repository.ChannelRepository;
import media.wepg.prototype.util.BulkResponseResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class EsChannelQuery {

    private final ElasticsearchClient esClient;
    private final ChannelRepository channelRepository;


    private static final String CHANNEL_INDEX_NAME = "prototype-channels";

    @Autowired
    public EsChannelQuery(ElasticsearchClient esClient, ChannelRepository channelRepository) {
        this.esClient = esClient;
        this.channelRepository = channelRepository;
    }


    public Optional<Channel> getDocumentById(Long id) throws IOException {
        GetResponse<Channel> response = esClient.get(g -> g
                        .index(CHANNEL_INDEX_NAME)
                        .id(id.toString()),
                Channel.class
        );

        if (response.found()) {
            Channel channel = response.source();
            log.info("Channel name" + channel.getChannelName());
            return Optional.of(channel);
        }

        log.info("Channel not found!");
        return Optional.empty();
    }

    public void fetchAndIndexChannelData() throws IOException {
        List<ChannelOrigin> channelOriginData = channelRepository.findAll();

        createNewIndexIfNotExists(CHANNEL_INDEX_NAME);

        indexChannelData(channelOriginData);
    }

    public boolean validateIndexExisting(String indexName) throws IOException {
        return esClient.indices().exists(i -> i.index(indexName)).value();
    }

    private void createNewIndexIfNotExists(String indexName) throws IOException {
        if (!validateIndexExisting(indexName)) {
            esClient.indices().create(i -> i.index(indexName)
            );
        }
    }


    private void indexChannelData(List<ChannelOrigin> channels) {
        channels.forEach(this::bulkChannelIndex);
    }

    private void bulkChannelIndex(ChannelOrigin channel) throws RuntimeException {
        BulkResponse bulkResponse;
        try {
            bulkResponse = getChannelDataBulkResponse(channel);
            BulkResponseResolver.resolveBulkResponse(bulkResponse, log);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private BulkResponse getChannelDataBulkResponse(ChannelOrigin ch) throws IOException {
        return esClient.bulk(new BulkRequest.Builder().operations(op ->
                op.index(idx -> idx
                        .index(CHANNEL_INDEX_NAME)
                        .id(ch.getId().toString())
                        .document(ch)
                )
        ).build());
    }
}
