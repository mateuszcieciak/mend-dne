package com.cieciak.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DneSequenceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnOkWithTrueResponseBodyIfRequestContainsDneSequence() throws Exception {
        mockMvc.perform(post("/server")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "seq": [1, 3, 2]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }
}
