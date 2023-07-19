package media.wepg.prototype.es.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration {

    @Value("${elasticsearch.url}")
    private String url;

    @Value("${elasticsearch.port}")
    private int port;

    // configure the URL and port on which Elasticsearch is running
    @Bean
    public RestClient getRestClient() {
        return RestClient.builder(
                new HttpHost(url, port)
        ).build();
    }

    // returns the Transport Object, whose purpose is it automatically map the Model Class to JSON and integrates them with API client
    @Bean
    public ElasticsearchTransport getElasticsearchTransport() {
        return new RestClientTransport(
                getRestClient(), new JacksonJsonpMapper()
        );
    }

    // returns a bean of elasticsearchclient, which we further use to perform all query operation with ElasticSearch
    @Bean
    public ElasticsearchClient getElasticsearchClient() {
        return new ElasticsearchClient(getElasticsearchTransport());
    }

}
