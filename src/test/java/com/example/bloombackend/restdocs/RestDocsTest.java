package com.example.bloombackend.restdocs;

import com.example.bloombackend.restdocstest.controller.dto.request.UserTestRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class RestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("API - RestDocs 테스트")
    void deleteCommentTest() throws Exception {
        // Given
        UserTestRequest user = new UserTestRequest("bloom", 25);

        // When & Then
        mockMvc.perform(post("/api/test/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isOk())

                .andDo(document("api-user-test",
                        requestFields(
                                fieldWithPath("name").description("사용자의 이름"),
                                fieldWithPath("age").description("사용자의 나이")
                        ),
                        responseFields(
                                fieldWithPath("name").description("사용자의 이름"),
                                fieldWithPath("age").description("사용자의 나이")
                        )
                ));
    }
}