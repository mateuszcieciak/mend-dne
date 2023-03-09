package com.cieciak.web;

import com.cieciak.application.DneSequenceCheckerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DneSequenceController.class)
public class DneSequenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DneSequenceCheckerService dneSequenceCheckerService;

    @Test
    public void shouldReturnOkWithBooleanResponseBodyIfRequestIsCorrectAndServiceReturnsBoolean() throws Exception {
        int[] inputSequence = {1, 2, 3};
        when(dneSequenceCheckerService.containsDneSequence(inputSequence)).thenReturn(true);

        mockMvc.perform(post("/server")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "seq": [1, 2, 3]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void shouldReturnUnprocessableEntityWithErrorMessageIfServiceThrownIllegalArgumentException() throws Exception {
        int[] inputSequence = {1, 2};
        when(dneSequenceCheckerService.containsDneSequence(inputSequence))
                .thenThrow(new IllegalArgumentException("array size is less than 3"));

        mockMvc.perform(post("/server")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "seq": [1, 2]
                                }
                                """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(HttpStatus.UNPROCESSABLE_ENTITY.name()))
                .andExpect(jsonPath("$.message").value("array size is less than 3"));
    }

    @Test
    public void shouldReturnBadRequestWithErrorMessageIfRequestDoesNotContainOnlyIntegersArray() throws Exception {
        mockMvc.perform(
                        post("/server")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "seq": [1, 2, "invalid"]
                                        }
                                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value("Invalid JSON request"));
    }

    @Test
    public void shouldReturnBadRequestWithErrorMessageIfRequestHasMalformedJsonStructure() throws Exception {
        mockMvc.perform(
                        post("/server")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "seq": [1, 2
                                        }
                                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value("Invalid JSON request"));
    }

    @Test
    public void shouldReturnBadRequestWithValidationMessageIfRequestDoesNotContainMandatoryField() throws Exception {
        mockMvc.perform(
                        post("/server")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "se": [1, 2, 3]
                                        }
                                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value("seq is mandatory in request body"));
    }
}