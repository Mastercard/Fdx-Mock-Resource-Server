package com.mastercard.fdx.mock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.fdx.mock.dto.DataUploadResponsePojo;
import com.mastercard.fdx.mock.dto.ErrorPojo;
import com.mastercard.fdx.mock.entity.*;
import com.mastercard.fdx.mock.enums.AccountCategory;
import com.mastercard.fdx.mock.exception.ApiException;
import com.mastercard.fdx.mock.repository.*;
import com.mastercard.fdx.mock.transaction.dto.TransactionsDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DataUploadService {

	ObjectMapper mapper = new ObjectMapper();


	@Autowired
	DepositAccountsRepository depositAccountsRepository;

	@Autowired
	LoanAccountsRepository loanAccountsRepository;

	@Autowired
	InvestmentAccountsRepository investmentAccountsRepository;

	@Autowired
	LineOfCreditAccountsRepository lineOfCreditAccountsRepository;

	@Autowired
	AccountConsentRepository accountConsentRepository;

	@Autowired
	FdxUserRepository fdxUserRepository;

	@Autowired
	TransactionsRepository transactionsRepository;

	private static String INV_ACC_CAT = "Invalid Account Category";

	public ResponseEntity<DataUploadResponsePojo> addAccount(String userId,List<Object> accountJsonObject) throws JsonProcessingException, JSONException{
		try {
			validateUserId(userId);
		} catch (ApiException e) {
			return new ResponseEntity<>(new DataUploadResponsePojo(e.getErrorPojo().getErrorCode(), e.getErrorPojo().getErrorMessage()), HttpStatus.BAD_REQUEST);
		}
		DataUploadResponsePojo dataUploadResponsePojo = new DataUploadResponsePojo();
		List<String> allAccountId=new ArrayList<>();
		for (Object entry: accountJsonObject) {
			JSONObject jsonObject = new JSONObject(mapper.writeValueAsString(entry));
			String accountCategory = jsonObject.getString("accountCategory");
			try {
				validateAccountCategory(accountCategory);

				if(StringUtils.equalsIgnoreCase("DEPOSIT_ACCOUNT", accountCategory)) {
					DepositAccount depositAccount = mapper.readValue(jsonObject.toString(), DepositAccount.class);
					DepositAccount response=depositAccountsRepository.save(depositAccount);
					allAccountId.add(String.valueOf(response.getInstitutionAccountId()));
				}

				else if(StringUtils.equalsIgnoreCase("LOAN_ACCOUNT", accountCategory)) {
					LoanAccount loanAccount = mapper.readValue(jsonObject.toString(), LoanAccount.class);
					LoanAccount response=loanAccountsRepository.save(loanAccount);
					allAccountId.add(String.valueOf(response.getInstitutionAccountId()));
				}
				else if(StringUtils.equalsIgnoreCase("INVESTMENT_ACCOUNT", accountCategory)) {
					InvestmentAccount investmentAccount = mapper.readValue(jsonObject.toString(), InvestmentAccount.class);
					InvestmentAccount response=investmentAccountsRepository.save(investmentAccount);
					allAccountId.add(String.valueOf(response.getInstitutionAccountId()));
				}
				else if(StringUtils.equalsIgnoreCase("LOC_ACCOUNT", accountCategory)) {
					LineOfCreditAccount lineOfCreditAccount = mapper.readValue(jsonObject.toString(), LineOfCreditAccount.class);
					LineOfCreditAccount response=lineOfCreditAccountsRepository.save(lineOfCreditAccount);
					allAccountId.add(String.valueOf(response.getInstitutionAccountId()));
				}
				else {
					ErrorPojo errorPojo = new ErrorPojo(1002, INV_ACC_CAT);
					throw new ApiException(HttpStatus.BAD_REQUEST,errorPojo, INV_ACC_CAT);
				}
			}
			catch (ApiException e) {
				return new ResponseEntity<>(new DataUploadResponsePojo(e.getErrorPojo().getErrorCode(), e.getErrorPojo().getErrorMessage()), HttpStatus.BAD_REQUEST);
			}
			catch (Exception e) {
				log.info("Error occured while save account :: "+e.getMessage());
				DataUploadResponsePojo errorDataUploadResponsePojo = new DataUploadResponsePojo();
				errorDataUploadResponsePojo.setErrorCode(1004);
				errorDataUploadResponsePojo.setErrorMessage("Error occured while save account. Please check request body. ");
				return new ResponseEntity<>(errorDataUploadResponsePojo, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		AccountConsent accountConsent = new AccountConsent();
		accountConsent.setUserId(userId);

		AccountConsent existingAccount = accountConsentRepository.findAccountsByUserId(userId);
		if(Objects.nonNull(existingAccount) && !existingAccount.getAllAccountIds().isEmpty()) {
			allAccountId.addAll(existingAccount.getAllAccountIds());
			existingAccount.setAllAccountIds(allAccountId);
			accountConsentRepository.save(existingAccount);
		}
		else {
			accountConsent.setAllAccountIds(allAccountId);
			accountConsentRepository.save(accountConsent);
		}
		dataUploadResponsePojo.setCode(0);
		dataUploadResponsePojo.setMessage("Success");
		return new ResponseEntity<>(dataUploadResponsePojo,HttpStatus.OK);
	}

	public ResponseEntity<DataUploadResponsePojo> addTransactions(List<TransactionsDetails> transactionsDetails){
		transactionsRepository.saveAll(transactionsDetails);
		DataUploadResponsePojo dataUploadResponsePojo = new DataUploadResponsePojo();
		dataUploadResponsePojo.setCode(0);
		dataUploadResponsePojo.setMessage("Success");
		return new ResponseEntity<>(dataUploadResponsePojo,HttpStatus.OK);
	}

	private void validateAccountCategory(String accountCategory) throws ApiException {
		if(!AccountCategory.validate(accountCategory)) {
			ErrorPojo errorPojo = new ErrorPojo(1002, INV_ACC_CAT);
			throw new ApiException(HttpStatus.BAD_REQUEST,errorPojo, INV_ACC_CAT);
		}
	}

	private void validateUserId(String userId) throws ApiException {
		FdxUser fdxUser = fdxUserRepository.findByUserId(userId);
		if(Objects.isNull(fdxUser))
		{
			ErrorPojo errorPojo = new ErrorPojo(1001, "No User Found");
			throw new ApiException(HttpStatus.BAD_REQUEST,errorPojo, "No User Found");
		}
	}
}
