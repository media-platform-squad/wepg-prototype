package media.wepg.prototype.es.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import media.wepg.prototype.orig.model.Program;
import media.wepg.prototype.orig.repository.ProgramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;


@Repository
public class ElasticSearchBatchQuery {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchBatchQuery.class);

    ElasticsearchClient elasticsearchClient;
    ProgramRepository programRepository;

    private final static Integer PAGE_SIZE = 1000;
    private static final String PROGRAM_INDEX_NAME = "prototype-programs";


    @Autowired
    public ElasticSearchBatchQuery(ElasticsearchClient elasticsearchClient, ProgramRepository programRepository) {
        this.elasticsearchClient = elasticsearchClient;
        this.programRepository = programRepository;
    }

    public boolean validateIndexExisting(String indexName) throws IOException {
        return elasticsearchClient.indices().exists(i -> i.index(indexName)).value();
    }

    private void createNewIndexIfNotExists(String indexName) throws IOException {
        IndexSettings indexSettings = new IndexSettings.Builder()
                .numberOfShards("5")
                .numberOfReplicas("1")
                .refreshInterval(Time.of(t -> t.time("5s")))
                .maxResultWindow(1000)
                .build();

        if (!validateIndexExisting(indexName)) {
            elasticsearchClient.indices().create(i -> i
                    .index(indexName)
                    .settings(indexSettings)
            );
        }
    }


    public void fetchAndIndexProgramData() throws IOException {
        int page = 0;
        List<Program> batchData;

        createNewIndexIfNotExists(PROGRAM_INDEX_NAME);

        do {
            Page<Program> programPage = programRepository.findByEventStartDateAndEventEndDate(
                    PageRequest.of(page++, PAGE_SIZE)
            );
            logger.info("programPage = " + page + programPage.getNumberOfElements());
            batchData = programPage.getContent();
            indexDataIntoElasticSearch(batchData);
        } while (!batchData.isEmpty());
    }


    private void indexDataIntoElasticSearchByBatch(List<Program> data) throws IOException {

        BulkResponse bulkResponse = bulkBatchData(data);

        if (bulkResponse.errors()) {
            logger.error("Bulk had errors");
            for (BulkResponseItem item : bulkResponse.items()) {
                if (item.error() != null) {
                    logger.error(item.error().reason());
                }
            }
        } else {
            logger.info("Bulk Request successful");
            for (BulkResponseItem item : bulkResponse.items()) {
                // String indexResponse = item.index();
            }
        }
    }


    private void indexDataIntoElasticSearch(List<Program> data) throws IOException {

        for (Program program : data) {
            String documentId = generateCustomDocumentId(program.getServiceId().toString(), program.getEventStartDate().toString());
            BulkRequest.Builder br = new BulkRequest.Builder().operations(op -> op
                    .index(idx -> idx
                            .index(PROGRAM_INDEX_NAME)
                            .id(documentId)
                            .document(program)
                    )
            );

            BulkResponse bulkResponse = elasticsearchClient.bulk(br.build());

            if (bulkResponse.errors()) {
                logger.error("Bulk had errors");
                for (BulkResponseItem item : bulkResponse.items()) {
                    if (item.error() != null) {
                        logger.error(item.error().reason());
                    }
                }
            } else {
                logger.info("Bulk Request successful");
                for (BulkResponseItem item : bulkResponse.items()) {
                    String indexResponse = item.index();
                }
            }
        }
    }


    @Deprecated
    private BulkResponse bulkBatchData2(List<Program> data) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Program program : data) {
            String documentId = generateCustomDocumentId(program.getServiceId().toString(), program.getEventStartDate().toString());
            br.operations(op -> op
                    .index(idx -> idx
                            .index(PROGRAM_INDEX_NAME)
                            .id(documentId)
                            .document(program)
                    )
            );
        }

        BulkResponse result = elasticsearchClient.bulk(br.build());

        if (result.errors()) {
            logger.error("Bulk had errors");
            for (BulkResponseItem item : result.items()) {
                logger.error(item.error().reason());
            }
        }

        return result;
    }


    private BulkResponse bulkBatchData(List<Program> data) throws IOException {
        return elasticsearchClient.bulk(builder -> {
                    for (Program program : data) {
                        String documentId = generateCustomDocumentId(program.getServiceId().toString(), program.getEventStartDate().toString());
                        logger.info("documentId = " + documentId);
                        builder.index(PROGRAM_INDEX_NAME)
                                .operations(ob -> {
                                    ob.index(ib -> ib.document(program).id(documentId));
                                    return ob;
                                });
                    }
                    return builder;
                }
        );
    }


    private String generateCustomDocumentId(String column1Value, String column2Value) {
        logger.info("column1Value = " + column1Value);
        logger.info("column2Value = " + column2Value);
        return column1Value + "_" + column2Value;
    }

}
