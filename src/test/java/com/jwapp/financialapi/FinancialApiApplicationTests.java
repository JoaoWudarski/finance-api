package com.jwapp.financialapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwapp.financialapi.adapters.outbound.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FinancialApiApplicationTests {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	protected UserRepository userRepository;

	@MockBean
	protected AccountRepository accountRepository;

	@MockBean
	protected AccountTransactionRepository accountTransactionRepository;

	@MockBean
	protected CardRepository cardRepository;

	@MockBean
	protected CardTransactionRepository cardTransactionRepository;

	@Test
	void contextLoads() {}

	protected MockHttpServletResponse performGet(String url, HttpStatus statusExpected, String fileNameExpected) throws Exception {
		MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get(url))
				.andExpect(status().is(statusExpected.value()))
				.andReturn().getResponse();
		assertJsonFileEquals(fileNameExpected, mockHttpServletResponse.getContentAsString());
		return mockHttpServletResponse;
	}

	protected MockHttpServletResponse performPost(String url, HttpStatus statusExpected,
												  String fileNameRequest, String fileNameExpected) throws Exception {
		MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(post(url)
						.contentType("application/json")
						.content(getJsonFile(fileNameRequest)))
				.andExpect(status().is(statusExpected.value()))
				.andReturn().getResponse();
		assertJsonFileEquals(fileNameExpected, mockHttpServletResponse.getContentAsString());
		return mockHttpServletResponse;
	}

	protected String getJsonFile(String fileName) throws IOException {
		File file = ResourceUtils.getFile("classpath:" + fileName);
		return new String(java.nio.file.Files.readAllBytes(file.toPath()));
	}

	protected void assertJsonFileEquals(String fileNameExpected, String jsonReturned) throws IOException {
		File file = ResourceUtils.getFile("classpath:" + fileNameExpected);
		JsonNode expectedJson = objectMapper.readTree(file);
		JsonNode actualJson = objectMapper.readTree(jsonReturned);
		assertEquals(expectedJson, actualJson);
	}
}
