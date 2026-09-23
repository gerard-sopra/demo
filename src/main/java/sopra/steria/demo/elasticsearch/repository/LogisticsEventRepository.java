package sopra.steria.demo.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sopra.steria.demo.elasticsearch.document.LogisticsEventDocument;

public interface LogisticsEventRepository
        extends ElasticsearchRepository<LogisticsEventDocument, String> {
}