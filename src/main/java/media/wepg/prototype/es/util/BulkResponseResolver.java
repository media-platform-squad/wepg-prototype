package media.wepg.prototype.es.util;

import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import org.slf4j.Logger;

public class BulkResponseResolver {
    public static void resolveBulkResponse(BulkResponse bulkResponse, Logger logger) {
        if (bulkResponse.errors()) {
            logger.error("Bulk had errors");
            for (BulkResponseItem item : bulkResponse.items())
                if (item.error() != null) {
                    logger.error(item.error().reason());
                }
        } else {
            logger.info("Bulk Request successful");
            for (BulkResponseItem item : bulkResponse.items()) {
                String indexResponse = item.index();
                logger.info(indexResponse);
            }
        }
    }
}
