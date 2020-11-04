package cz.cvut.fit.gorgomat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class WelcomeTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void sampleTestSuccess() {
        assertEquals(1, 1);
    }

//    @Test
//    public void sampleTestFail() {
//        assertEquals("BI-TJV", "BI-PJV");
//    }

    @Test
    public void getWelcome() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/welcome")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "{\"text\":\"Welcome to Ski-Strahov!\"}")
                );
    }


}
