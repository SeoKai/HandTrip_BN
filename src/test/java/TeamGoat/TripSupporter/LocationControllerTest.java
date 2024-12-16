package TeamGoat.TripSupporter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // 추가
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // 추가

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetLocationsByRegion() throws Exception {
        mockMvc.perform(get("/api/locations/by-region")
                        .param("region", "도쿄"))
                .andExpect(status().isOk());
    }
}
