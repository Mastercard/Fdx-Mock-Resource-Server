package com.mastercard.fdx.mock.controller;

import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mastercard.fdx.mock.dto.DataUploadResponsePojo;
import com.mastercard.fdx.mock.service.DataUploadService;
import com.mastercard.fdx.mock.transaction.dto.TransactionsDetails;



@RestController
@RequestMapping("/upload")
public class DataUploadController {
	
	
	@Autowired
	DataUploadService dataUploadService;
	 

	@PostMapping(value = "/addAccount" ,produces = { "application/json" },
            consumes = { "application/json" })
	public ResponseEntity<DataUploadResponsePojo> addAccount(@RequestParam String userId,@RequestBody List<Object> accountJsonObject) throws JsonProcessingException, JSONException {
		return dataUploadService.addAccount(userId, accountJsonObject);
	}
	
	@PostMapping(value = "/addTransactions")
	public ResponseEntity<DataUploadResponsePojo> addTransactions(@RequestBody List<TransactionsDetails> transactionsDetails){
		return dataUploadService.addTransactions(transactionsDetails);
	}

	
}
