package media.wepg.prototype.util;

import co.elastic.clients.elasticsearch.core.BulkResponse;
import org.slf4j.Logger;

public class BulkResponseResolver {
    public static void resolveBulkResponse(BulkResponse bulkResponse, Logger logger) {
        if (bulkResponse.errors()) {
            logger.error("Bulk had errors");
            bulkResponse.items().forEach(item -> {
                if (item.error() != null) {
                    logger.error(item.error().reason());
                }
            });
            return;
        }

        logger.info("Bulk Request successful");
        bulkResponse.items().forEach(item -> logger.info(item.index()));
    }
}
