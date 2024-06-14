package com.mastercard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.fdx.mock.controller.DataUploadController;
import com.mastercard.fdx.mock.service.DataUploadService;
import com.mastercard.fdx.mock.transaction.dto.TransactionsDetails;

@ExtendWith(MockitoExtension.class)
class DataUploadControllerTest {

	@InjectMocks
	DataUploadController dataUploadController;
	
	@Mock
	DataUploadService dataUploadService;
	
	private MockMvc mockMvc;
	ObjectMapper objectMapper = new ObjectMapper();
	
	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(dataUploadController).build();
	}
	
	@Test
	void testAddAccount() throws Exception {
		when(dataUploadService.addAccount(any(), any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		List<Object> list = new ArrayList<>();
		mockMvc.perform(post("/upload/addAccount?userId=fdxuser").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(list))).andExpect(status().isOk());
	}
	
	@Test
	void testAddTransactions() throws Exception {
		when(dataUploadService.addTransactions(any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		List<TransactionsDetails> list = new ArrayList<>();
		mockMvc.perform(post("/upload/addTransactions").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(list))).andExpect(status().isOk());
	}
}
