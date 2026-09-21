package sopra.steria.demo.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import sopra.steria.demo.base.Base;
import sopra.steria.demo.base.BaseRepository;
import sopra.steria.demo.inventory.SupplyType;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class SupplyRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BaseRepository baseRepository;

    @Test
    void shouldCreateSupplyRequest() throws Exception {

        Base base = new Base();
        base.setName("Test FOB");
        base.setLocation("Kontum");

        base = baseRepository.save(base);

        CreateSupplyRequest request = new CreateSupplyRequest(
                base.getId(),
                SupplyType.AMMUNITION,
                500,
                Priority.HIGH
        );

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.supplyType").value("AMMUNITION"))
                .andExpect(jsonPath("$.quantity").value(500))
                .andExpect(jsonPath("$.priority").value("HIGH"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldRejectNegativeQuantity() throws Exception {

        Base base = new Base();
        base.setName("Test FOB");
        base.setLocation("Kontum");

        base = baseRepository.save(base);

        CreateSupplyRequest request = new CreateSupplyRequest(
                base.getId(),
                SupplyType.FUEL,
                -100,
                Priority.NORMAL
        );

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn404WhenBaseDoesNotExist() throws Exception {

        CreateSupplyRequest request = new CreateSupplyRequest(
                UUID.randomUUID(),
                SupplyType.MEDICAL,
                100,
                Priority.CRITICAL
        );

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void shouldFindSupplyRequestById() throws Exception {

        Base base = new Base();
        base.setName("Test FOB");
        base.setLocation("Dak To");

        base = baseRepository.save(base);

        CreateSupplyRequest request = new CreateSupplyRequest(
                base.getId(),
                SupplyType.FOOD,
                250,
                Priority.NORMAL
        );

        String response = mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        SupplyRequest created =
                objectMapper.readValue(response, SupplyRequest.class);

        mockMvc.perform(get("/api/requests/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId().toString()))
                .andExpect(jsonPath("$.supplyType").value("FOOD"))
                .andExpect(jsonPath("$.quantity").value(250))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldReturn404WhenSupplyRequestDoesNotExist() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(get("/api/requests/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}