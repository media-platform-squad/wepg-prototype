package media.wepg.prototype.config.elasticsearch;

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
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
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


    @Deprecated
    @Bean
    public RestHighLevelClient restHighLevelClient() throws Exception {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(new TrustAllStrategy())
                .build();

        HttpHost[] httpHosts = uris.stream()
                .map(uri -> new HttpHost(uri, 9200, "http"))
                .toArray(HttpHost[]::new);

        return new RestHighLevelClient(
                RestClient.builder(httpHosts)
                        .setHttpClientConfigCallback(httpClientBuilder ->
                                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                                        .setSSLContext(sslContext)
                                        .setSSLHostnameVerifier(new NoopHostnameVerifier()))
        );
    }

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


        HttpHost[] httpHosts = getEsServerHosts();

        return RestClient.builder(httpHosts)
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                                .setSSLContext(sslContext)
                                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE))
                .build();
    }

    private HttpHost[] getEsServerHosts() {
        return uris.stream()
                .map(uri -> new HttpHost(uri, 9200, "http"))
                .toArray(HttpHost[]::new);
    }
}
