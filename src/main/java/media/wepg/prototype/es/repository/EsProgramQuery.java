package media.wepg.prototype.es.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import lombok.extern.slf4j.Slf4j;
import media.wepg.prototype.es.model.Program;
import media.wepg.prototype.es.util.BulkResponseResolver;
import media.wepg.prototype.orig.model.ProgramOrigin;
import media.wepg.prototype.orig.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class EsProgramQuery {

    private final ElasticsearchClient esClient;
    private final ProgramRepository programRepository;

    private static final int PAGE_SIZE = 1000;
    private static final String PROGRAM_INDEX_NAME = "prototype-programs";
    private static final String SERVICE_ID_FIELD_NAME = "serviceId";
    private static final String EVENT_START_DATE_FIELD_NAME = "eventStartDate";


    @Autowired
    public EsProgramQuery(ElasticsearchClient esClient, ProgramRepository programRepository) {
        this.esClient = esClient;
        this.programRepository = programRepository;
    }

    private static Query getQueryByServiceId(Long serviceId) {
        return MatchQuery.of(m -> m
                .field(SERVICE_ID_FIELD_NAME)
                .query(serviceId)
        )._toQuery();
    }

    private static Query getQueryByEventStartDate(LocalDateTime startDate) {
        return RangeQuery.of(m -> m
                .field(EVENT_START_DATE_FIELD_NAME)
                .from(startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd'00:00:000'")))
                .to(startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd'23:59:000'")))
        )._toQuery();
    }

    private SearchResponse<Program> getQueryResultsByServiceIdAndEventStartDate(Query byServiceId, Query byEventStartDate) throws IOException {
        return esClient.search(s -> s
                        .index(PROGRAM_INDEX_NAME)
                        .query(q -> q.bool(b -> b
                                        .must(byServiceId)
                                        .must(byEventStartDate)
                                )
                        ),
                Program.class
        );
    }

    public List<Program> getDocumentByServiceIdAndEventStartDate(Long serviceId, LocalDateTime startDate) {
        List<Program> programResult = new ArrayList<>();

        Query byServiceId = getQueryByServiceId(serviceId);
        Query byEventStartDate = getQueryByEventStartDate(startDate);

        try {
            SearchResponse<Program> responseResult = getQueryResultsByServiceIdAndEventStartDate(byServiceId, byEventStartDate);
            TotalHits totalHits = responseResult.hits().total();

            resolveResultCount(totalHits);
            return getResultPrograms(programResult, responseResult);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return programResult;
    }

    private static void resolveResultCount(TotalHits totalHits) {
        boolean isExactResult = totalHits.relation() == TotalHitsRelation.Eq;

        if (isExactResult) {
            log.info("There are " + totalHits.value() + " results");
        } else {
            log.info("There are more than " + totalHits.value() + " results");
        }
    }

    private static List<Program> getResultPrograms(List<Program> programResult, SearchResponse<Program> responseResult) {
        List<Hit<Program>> hits = responseResult.hits().hits();
        for (Hit<Program> hit : hits) {
            Program program = hit.source();
            programResult.add(program);
            log.info("Found product " + program.getTitleName() + ", score " + hit.score());
        }
        return programResult;
    }


    public void fetchAndIndexProgramData() throws IOException {
        int page = 0;
        List<ProgramOrigin> batchData;

        createNewIndexIfNotExists(PROGRAM_INDEX_NAME);

        do {
            Page<ProgramOrigin> programPage = programRepository.findByEventStartDateAndEventEndDate(
                    PageRequest.of(page++, PAGE_SIZE)
            );
            log.info("programPage = " + page + " " + programPage.getNumberOfElements());
            batchData = programPage.getContent();
            indexProgramData(batchData);
        } while (!batchData.isEmpty());
    }


    public boolean validateIndexExisting(String indexName) throws IOException {
        return esClient.indices().exists(i -> i.index(indexName)).value();
    }

    private void createNewIndexIfNotExists(String indexName) throws IOException {
        if (!validateIndexExisting(indexName)) {
            esClient.indices().create(i -> i
                    .index(indexName)
            );
        }
    }

    private void indexProgramData(List<ProgramOrigin> programOrigins) {
        BulkResponse bulkResponse;
        if (programOrigins.isEmpty()) {
            return;
        }
        try {
            bulkResponse = bulkBatchData(programOrigins);
            BulkResponseResolver.resolveBulkResponse(bulkResponse, log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private BulkResponse bulkBatchData(List<ProgramOrigin> data) throws RuntimeException, IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        data.forEach(d -> br.operations(op -> op
                .index(idx -> idx
                        .index(PROGRAM_INDEX_NAME)
                        .document(d))
        ));

        return esClient.bulk(br.build());
    }

}
