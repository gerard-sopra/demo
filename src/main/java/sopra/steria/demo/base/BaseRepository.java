package sopra.steria.demo.base;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BaseRepository extends JpaRepository<Base, UUID> {

    List<Base> findByType(BaseType type);
}
