package com.kakaopay.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.kakaopay.service.DivideMoneyService;

@WebMvcTest(DivideMoneyController.class)
class DivideMoneyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private DivideMoneyService service;
    
	@BeforeEach
	void setUp() throws Exception {
	}
	
	String oldTokken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaXZpZGVNb25leSIsIm1vbmV5IjoyMDAwMCwiZ3JvdXBJZCI6Im12bWRraWVtQG5hdmVyLmNvbS1UMTAwMjEyLVR1ZSBNYXIgMDIgMTA6NTk6NTMgS1NUIDIwMjEiLCJjb3VudCI6MywicmVnZGF0ZSI6MTYxNDY1MDM5MzA3MiwiWC1VU0VSLUlEIjoibXZtZGtpZW1AbmF2ZXIuY29tIiwiZXhwIjoxNjE1MjU1MTkzLCJYLVJPT00tSUQiOiJUMTAwMjEyIn0.26uaUvp6PGdxpk4OKZZIDiOACImDbWN58ddUDJeM9E8";
	String newTokken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaXZpZGVNb25leSIsIm1vbmV5IjoyMDAwMCwiZ3JvdXBJZCI6Im12bWRraWVtQG5hdmVyLmNvbS1UMTAwMjEyLVR1ZSBNYXIgMDIgMTU6MDU6NDggS1NUIDIwMjEiLCJjb3VudCI6MywicmVnZGF0ZSI6MTYxNDY2NTE0ODkxMiwiWC1VU0VSLUlEIjoibXZtZGtpZW1AbmF2ZXIuY29tIiwiZXhwIjoxNjE1MjY5OTQ4LCJYLVJPT00tSUQiOiJUMTAwMjEyIn0.S57E2xvrpbQXwhbAH4BqpwyHS19YoiND-LcwPdS87t0";

	@Test
	void testGet() throws Exception{
		//조회 api Controller 및 통합 테스트 케이스
        final ResultActions actions = mockMvc
        		.perform(get("/v1/api/divide")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-USER-ID", "mvmdkiem@naver.com")
                .header("X-ROOM-ID", "T100212")
        		.param("tokken", newTokken));

        actions
        		.andDo(print());
	}

	@Test
	void testPost() throws Exception {
		//돈 뿌리기 생성 api Controller 및 통합 테스트 케이스
	    String jsonStr="{\"money\": \"20000\", \"count\": \"3\"} ";

        final ResultActions actions = mockMvc
        		.perform(post("/v1/api/divide")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr)
                .header("X-USER-ID", "mvmdkiem@naver.com")
                .header("X-ROOM-ID", "T100212"));

        actions
        		.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testPut() throws Exception {
		//돈 줍기 api Controller 및 통합 테스트 케이스
		String jsonStr="{\"money\": \"20000\", \"count\": \"3\"} ";

        final ResultActions actions = mockMvc
        		.perform(put("/v1/api/divide")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr)
                .header("X-USER-ID", "mvmdkiem@naver.com")
                .header("X-ROOM-ID", "T100212")
        		.param("tokken", newTokken));

        actions
        		.andDo(print()).andExpect(status().isOk());
	}

}
