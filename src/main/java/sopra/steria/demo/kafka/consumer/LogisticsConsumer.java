package sopra.steria.demo.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sopra.steria.demo.kafka.event.SupplyRequestedEvent;
import sopra.steria.demo.logistics.LogisticsService;

@Component
public class LogisticsConsumer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LogisticsConsumer.class);

    private final LogisticsService logisticsService;

    public LogisticsConsumer(LogisticsService logisticsService) {
        this.logisticsService = logisticsService;
    }

    @KafkaListener(topics = "supply.requested")
    public void consume(SupplyRequestedEvent event) {

        LOGGER.info(
                "Processing supply request: requestId={}, baseId={}, supplyType={}, quantity={}",
                event.requestId(),
                event.baseId(),
                event.supplyType(),
                event.quantity()
        );

        logisticsService.processSupplyRequest(event);

        LOGGER.info(
                "Supply request {} processed",
                event.requestId()
        );
    }
}