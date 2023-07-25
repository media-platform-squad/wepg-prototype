package media.wepg.prototype.config.elasticsearch;

import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.elasticsearch.index")
public class IndexSettingsConfig {

    private String number_of_shards;
    private String number_of_replicas;
    private String refresh_interval;
    private int max_result_window;


    @Bean
    public IndexSettings esIndexSettings() {
        return new IndexSettings.Builder()
                .numberOfShards(number_of_shards)
                .numberOfReplicas(number_of_replicas)
                .refreshInterval(Time.of(t -> t.time(refresh_interval)))
                .maxResultWindow(max_result_window)
                .build();
    }
}
