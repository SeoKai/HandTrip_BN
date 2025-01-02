package TeamGoat.TripSupporter.Controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // 추가
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTest {

    private static final Logger log = LoggerFactory.getLogger(LocationControllerTest.class);
    @Autowired
    private MockMvc mockMvc;


    @DisplayName("사용자가 선택한 여행지의 주위 도시를 잘 전달하는지 확인")
    @Test
    public void 여행지근처도시전달테스트() throws Exception {
        mockMvc.perform(get("/api/locations/by-region")
                        .param("regionId", "1")) // 요청 파라미터 추가
                .andExpect(status().isOk()) // 200 상태 코드 기대
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(149))
                .andDo(print());
    }

    @DisplayName("실패테스트 사용자 선택 여행지 주위 도시 잘 전달하는지 확인")
    @Test
    public void 여행지근처도시전달실패테스트() throws Exception {
        mockMvc.perform(get("/api/locations/by-region")
                        .param("regionId", "5")) // 요청 파라미터 추가
                .andExpect(status().isOk()) // 200 상태 코드 기대
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(149))
                .andDo(print());
    }

}
