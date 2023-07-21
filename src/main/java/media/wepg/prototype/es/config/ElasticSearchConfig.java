package media.wepg.prototype.es.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Setter
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.elasticsearch")
public class ElasticSearchConfig {

    private final ObjectMapper objectMapper;
    private List<String> uris;
    private String username;
    private String password;


    @Bean
    public ElasticsearchTransport getElasticsearchTransport() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return new RestClientTransport(getRestClient(), new JacksonJsonpMapper(objectMapper));
    }

    @Bean
    public ElasticsearchClient getElasticsearchClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return new ElasticsearchClient(getElasticsearchTransport());
    }

    @Bean
    public RestClient getRestClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        var credentials = new UsernamePasswordCredentials(username, password);

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        var sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, new TrustAllStrategy())
                .build();


        HttpHost[] httpHosts = uris.stream().map(uri -> new HttpHost(uri, 9200, "http")).toArray(HttpHost[]::new);

        return RestClient.builder(httpHosts)
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                                .setSSLContext(sslContext)
                                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE))
                .build();
    }
}
