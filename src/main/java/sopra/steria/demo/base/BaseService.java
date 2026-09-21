package sopra.steria.demo.base;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseService {
    private final BaseRepository baseRepository;

    public BaseService(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public List<Base> findAll() {
        return baseRepository.findAll();
    }
}
