package sopra.steria.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.data.elasticsearch.repositories.enabled=false"
})
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
