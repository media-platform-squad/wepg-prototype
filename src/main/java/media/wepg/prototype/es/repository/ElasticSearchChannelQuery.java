package media.wepg.prototype.es.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import media.wepg.prototype.es.model.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Optional;

@Repository
public class ElasticSearchChannelQuery {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchChannelQuery.class);

    private final ElasticsearchClient elasticsearchClient;

    private static final String CHANNEL_INDEX_NAME = "prototype-channels";
    private static final String DOCUMENT_CREATED_MESSAGE = "Created";
    private static final String DOCUMENT_UPDATED_MESSAGE = "Updated";
    private static final String DOCUMENT_CREATED_RESPONSE_MESSAGE = "Document has been successfully created";
    private static final String DOCUMENT_UPDATED_RESPONSE_MESSAGE = "Document has been successfully updated";
    private static final String DOCUMENT_FAILED_RESPONSE_MESSAGE = "Error while performing the operation.";


    @Autowired
    public ElasticSearchChannelQuery(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public boolean validateIndexExisting(String indexName) throws IOException {
        return elasticsearchClient.indices().exists(i -> i.index(indexName)).value();
    }

    public String createOrUpdateDocument(Channel channel) throws IOException {
        if (!validateIndexExisting(CHANNEL_INDEX_NAME)) {
            elasticsearchClient.indices().create(i -> i.index(CHANNEL_INDEX_NAME));
        }

        IndexResponse response = elasticsearchClient.index(i -> i
                .index(CHANNEL_INDEX_NAME)
                .id(channel.getId().toString())
                .document(channel)
        );

        String resultMessage = response.result().name();
        switch (resultMessage) {
            case DOCUMENT_CREATED_MESSAGE -> {
                return DOCUMENT_CREATED_RESPONSE_MESSAGE;
            }
            case DOCUMENT_UPDATED_MESSAGE -> {
                return DOCUMENT_UPDATED_RESPONSE_MESSAGE;
            }
            default -> {
                return DOCUMENT_FAILED_RESPONSE_MESSAGE;
            }
        }
    }

    public Optional<Channel> getDocumentById(Long id) throws IOException {
        GetResponse<Channel> response = elasticsearchClient.get(g -> g
                        .index(CHANNEL_INDEX_NAME)
                        .id(id.toString()),
                Channel.class
        );

        if (response.found()) {
            Channel channel = response.source();
            logger.info("Channel name" + channel.getChannelName());
            return Optional.of(channel);
        } else {
            logger.info("Channel not found!");
        }

        return Optional.empty();
    }

}
