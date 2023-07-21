package media.wepg.prototype.es.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ElasticSearchChannelQueryTest {
    @Autowired
    private ElasticSearchChannelQuery elasticSearchChannelQuery;

    @Test
    @DisplayName("생성한 적 없는 이름의 인덱스는 노드로부터 발견하지 못한다.")
    void validateIndexExisting_not_existing_index() throws IOException {
        assertThat(elasticSearchChannelQuery.validateIndexExisting("undefined-Index")).isFalse();
    }

    @Test
    @DisplayName("생성한 prototype channels 인덱스는 노드로부터 발견한다.")
    void validateIndexExisting_prototype_channels() throws IOException {
        assertThat(elasticSearchChannelQuery.validateIndexExisting("prototype-channels")).isTrue();
    }

    @Test
    void createOrUpdateDocument() {
    }

    @Test
    void getDocumentById() {
    }
}