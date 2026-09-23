package sopra.steria.demo.elasticsearch.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@Document(indexName = "logistics-events")
public class LogisticsEventDocument {

    @Id
    private String id;

    private String eventType;

    private UUID requestId;
    private UUID shipmentId;
    private UUID baseId;

    private String supplyType;
    private Integer quantity;
    private String priority;

    private Instant createdAt;

    public LogisticsEventDocument() {
    }
}