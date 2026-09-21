package sopra.steria.demo.request;

import org.springframework.stereotype.Service;
import sopra.steria.demo.base.Base;
import sopra.steria.demo.base.BaseRepository;
import sopra.steria.demo.exception.BaseNotFoundException;
import sopra.steria.demo.exception.SupplyRequestNotFoundException;
import sopra.steria.demo.kafka.event.SupplyRequestedEvent;
import sopra.steria.demo.kafka.producer.SupplyRequestProducer;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class SupplyRequestService {

    private final SupplyRequestRepository requestRepository;
    private final BaseRepository baseRepository;
    private final SupplyRequestProducer supplyRequestProducer;

    public SupplyRequestService(
            SupplyRequestRepository requestRepository,
            BaseRepository baseRepository,
            SupplyRequestProducer supplyRequestProducer) {

        this.requestRepository = requestRepository;
        this.baseRepository = baseRepository;
        this.supplyRequestProducer = supplyRequestProducer;
    }

    public SupplyRequest create(CreateSupplyRequest input) {

        Base base = baseRepository.findById(input.baseId())
                .orElseThrow(() ->
                        new BaseNotFoundException(input.baseId()));

        SupplyRequest request = new SupplyRequest();

        request.setBase(base);
        request.setSupplyType(input.supplyType());
        request.setQuantity(input.quantity());
        request.setPriority(input.priority());
        request.setStatus(RequestStatus.PENDING);
        request.setCreatedAt(Instant.now());

        SupplyRequest savedRequest = requestRepository.save(request);

        SupplyRequestedEvent event = new SupplyRequestedEvent(
                savedRequest.getId(),
                base.getId(),
                savedRequest.getSupplyType(),
                savedRequest.getQuantity(),
                savedRequest.getPriority(),
                savedRequest.getCreatedAt()
        );

        supplyRequestProducer.publish(event);

        return savedRequest;
    }

    public List<SupplyRequest> findAll() {
        return requestRepository.findAll();
    }

    public SupplyRequest findById(UUID id) {
        return requestRepository.findById(id)
                .orElseThrow(() ->
                        new SupplyRequestNotFoundException(id));
    }
}
