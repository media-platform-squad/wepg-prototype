package media.wepg.prototype.config.elasticsearch;

import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IndexSettingsConfig {

    @Bean
    public IndexSettings getIndexSettings() {
        return new co.elastic.clients.elasticsearch.indices.IndexSettings.Builder()
                .numberOfShards("5")
                .numberOfReplicas("1")
                .refreshInterval(Time.of(t -> t.time("5s")))
                .maxResultWindow(1000)
                .build();

    }
}
