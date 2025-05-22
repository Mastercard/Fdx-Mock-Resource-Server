package com.mastercard.fdx.mock.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.fdx.mock.entity.AccountConsent;
import com.mastercard.fdx.mock.entity.AccountContact;
import com.mastercard.fdx.mock.entity.AccountPaymentNetwork;
import com.mastercard.fdx.mock.entity.DepositAccount;
import com.mastercard.fdx.mock.entity.FdxUser;
import com.mastercard.fdx.mock.entity.InvestmentAccount;
import com.mastercard.fdx.mock.entity.LineOfCreditAccount;
import com.mastercard.fdx.mock.entity.LoanAccount;
import com.mastercard.fdx.mock.entity.Statement;
import com.mastercard.fdx.mock.repository.AccountConsentRepository;
import com.mastercard.fdx.mock.repository.AccountsRepository;
import com.mastercard.fdx.mock.repository.ContactRepository;
import com.mastercard.fdx.mock.repository.DepositAccountsRepository;
import com.mastercard.fdx.mock.repository.FdxUserRepository;
import com.mastercard.fdx.mock.repository.InvestmentAccountsRepository;
import com.mastercard.fdx.mock.repository.LineOfCreditAccountsRepository;
import com.mastercard.fdx.mock.repository.LoanAccountsRepository;
import com.mastercard.fdx.mock.repository.PaymentNetworksRepository;
import com.mastercard.fdx.mock.repository.StatementRepository;
import com.mastercard.fdx.mock.repository.TransactionsRepository;
import com.mastercard.fdx.mock.transaction.dto.InvestmentTransaction;
import com.mastercard.fdx.mock.transaction.dto.Transactions;
import com.mastercard.fdx.mock.utilities.CommonUtilities;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataLoader {

    private static String pwd = "password";

    /**
     * Below bean configuration loads the profile data at boot time.
     * All the data associated with each profiles like accounts, transactions, contacts, payment network, statements are
     * read from json and saved into h2 database.
     * @param accountsRepository
     * @param depositAccountsRepository
     * @param loanAccountsRepository
     * @param investmentAccountsRepository
     * @param lineOfCreditAccountsRepository
     * @param transactionsRepository
     * @param contactRepository
     * @param paymentNetworksRepository
     * @param consentRepository
     * @param fdxUserRepository
     * @param statementRepository
     * @return
     */
    @Bean
    @Transactional
    CommandLineRunner runner(AccountsRepository accountsRepository,
                             DepositAccountsRepository depositAccountsRepository,
                             LoanAccountsRepository loanAccountsRepository, InvestmentAccountsRepository investmentAccountsRepository,
                             LineOfCreditAccountsRepository lineOfCreditAccountsRepository,
                             TransactionsRepository transactionsRepository, ContactRepository contactRepository,
                             PaymentNetworksRepository paymentNetworksRepository, AccountConsentRepository consentRepository, FdxUserRepository fdxUserRepository,StatementRepository statementRepository) {
        return args -> {
            // read json and write to db
            ObjectMapper mapper = new ObjectMapper();
            try {
                profileCreated(depositAccountsRepository, loanAccountsRepository, investmentAccountsRepository,
                        lineOfCreditAccountsRepository, transactionsRepository, contactRepository,
                        paymentNetworksRepository, consentRepository, fdxUserRepository, mapper,statementRepository);

                profile1Created(depositAccountsRepository, loanAccountsRepository, investmentAccountsRepository,
                        transactionsRepository, contactRepository,
                        paymentNetworksRepository, consentRepository, fdxUserRepository, mapper);

                profile2Created(depositAccountsRepository, loanAccountsRepository,
                        transactionsRepository, contactRepository,
                        paymentNetworksRepository, consentRepository, fdxUserRepository, mapper);
                
                profile3Created(depositAccountsRepository, loanAccountsRepository,
                		 consentRepository, fdxUserRepository, mapper);


                log.info("All Profile and account data saved successfully");
            } catch (Exception e){
                log.error("Unable to save profile and account response: " + e.getMessage());
            }
        };
    }

   

	private void profileCreated(DepositAccountsRepository depositAccountsRepository,
                                LoanAccountsRepository loanAccountsRepository, InvestmentAccountsRepository investmentAccountsRepository,
                                LineOfCreditAccountsRepository lineOfCreditAccountsRepository,
                                TransactionsRepository transactionsRepository, ContactRepository contactRepository,
                                PaymentNetworksRepository paymentNetworksRepository, AccountConsentRepository consentRepository,
                                FdxUserRepository fdxUserRepository, ObjectMapper mapper,StatementRepository statementRepository)
            throws IOException {
        // 1. Create user
        FdxUser fdxUser = new FdxUser(0, "fdxuser", CommonUtilities.encrypthash(pwd),null);
        fdxUser = fdxUserRepository.save(fdxUser);

        //2. saving account for profile of fdxuser...
        AccountConsent accountConsent = new AccountConsent(0,"fdxuser", Arrays.asList("10001","20001","3746272","5326884"),null,0,"");
        consentRepository.save(accountConsent);

        LoanAccount loanAccounts = mapper.readValue(CommonUtilities.getFileContent("loan_acc_details.json"), LoanAccount.class);
        loanAccountsRepository.save(loanAccounts);

        DepositAccount depositAccount = mapper.readValue(CommonUtilities.getFileContent("deposit_acc_details.json"), DepositAccount.class);
        depositAccountsRepository.save(depositAccount);

        InvestmentAccount investmentAccount = mapper.readValue(CommonUtilities.getFileContent("investment_acc_details.json"), InvestmentAccount.class);
        investmentAccountsRepository.save(investmentAccount);

        LineOfCreditAccount lineOfCreditAccount = mapper.readValue(CommonUtilities.getFileContent("lineofcredit_acc_details.json"), LineOfCreditAccount.class);
        lineOfCreditAccountsRepository.save(lineOfCreditAccount);

        Transactions depositTransaction = mapper.readValue(CommonUtilities.getFileContent("depositTransaction.json"), Transactions.class);
        transactionsRepository.saveAll(depositTransaction.getTransactions());

        Transactions loanTransaction = mapper.readValue(CommonUtilities.getFileContent("loanTransaction.json"), Transactions.class);
        transactionsRepository.saveAll(loanTransaction.getTransactions());

        Transactions locTransaction = mapper.readValue(CommonUtilities.getFileContent("locTransaction.json"), Transactions.class);
        transactionsRepository.saveAll(locTransaction.getTransactions());


        List<InvestmentTransaction> investmentTransaction = mapper.readValue(CommonUtilities.getFileContent("investmentTransaction.json"),  new TypeReference<List<InvestmentTransaction>>() {});
        transactionsRepository.saveAll(investmentTransaction);

        AccountContact depositContact = mapper.readValue(CommonUtilities.getFileContent("deposit_contact_details.json"), AccountContact.class);
        contactRepository.save(depositContact);

        AccountContact loanContact = mapper.readValue(CommonUtilities.getFileContent("loan_contact_details.json"), AccountContact.class);
        contactRepository.save(loanContact);

        AccountContact investmentContact = mapper.readValue(CommonUtilities.getFileContent("investment_contact_details.json"), AccountContact.class);
        contactRepository.save(investmentContact);

        AccountContact locContact = mapper.readValue(CommonUtilities.getFileContent("loc_contact_details.json"), AccountContact.class);
        contactRepository.save(locContact);


        List<AccountPaymentNetwork> accountPaymentNetworkList =   mapper.readValue(CommonUtilities.getFileContent("payment_network_list.json"), new TypeReference<List<AccountPaymentNetwork>>() {});
        paymentNetworksRepository.saveAll(accountPaymentNetworkList);
        
        ClassPathResource resource = new ClassPathResource("sample-statements/40004.pdf");
        InputStream inputStream = resource.getInputStream();
        
        ClassPathResource resource1 = new ClassPathResource("sample-statements/50005.pdf");
        InputStream inputStream1 = resource1.getInputStream();
        
        
        Statement statement = new Statement();
        statement.setAccountId("10001");statement.setDescription("FFOS Monthly/Quarterly Statement");statement.setStatementDate(Calendar.getInstance());
        statement.setStatementId("40004");statement.setStatus("AVAILABLE");statement.setFile(inputStream.readAllBytes());
        
        Statement statement1 = new Statement();
        statement1.setAccountId("20001");statement1.setDescription("FFOS Year End Investment Report");statement1.setStatementDate(Calendar.getInstance());
        statement1.setStatementId("50005");statement1.setStatus("AVAILABLE");statement1.setFile(inputStream1.readAllBytes());
        
        statementRepository.save(statement);statementRepository.save(statement1);

        log.info("Profile save successfully");
    }

    private void profile1Created(DepositAccountsRepository depositAccountsRepository,
                                 LoanAccountsRepository loanAccountsRepository, InvestmentAccountsRepository investmentAccountsRepository,
                                 TransactionsRepository transactionsRepository, ContactRepository contactRepository,
                                 PaymentNetworksRepository paymentNetworksRepository, AccountConsentRepository consentRepository,
                                 FdxUserRepository fdxUserRepository, ObjectMapper mapper)
            throws JsonProcessingException, JsonMappingException {
        // 1. Create user
        FdxUser fdxUser = new FdxUser(0, "fdxuser1", CommonUtilities.encrypthash(pwd),null);
        fdxUserRepository.save(fdxUser);

        //2. saving account for profile of fdxuser1...
        AccountConsent accountConsent = new AccountConsent(0,"fdxuser1", Arrays.asList("6468990","4568922","500011"),null,0,"");
        consentRepository.save(accountConsent);

        LoanAccount loanAccounts = mapper.readValue(CommonUtilities.getFileContent("profile1/loan_acc_details1.json"), LoanAccount.class);
        loanAccountsRepository.save(loanAccounts);

        DepositAccount depositAccount = mapper.readValue(CommonUtilities.getFileContent("profile1/deposit_acc_details1.json"), DepositAccount.class);
        depositAccountsRepository.save(depositAccount);

        InvestmentAccount investmentAccount = mapper.readValue(CommonUtilities.getFileContent("profile1/investment_acc_details1.json"), InvestmentAccount.class);
        investmentAccountsRepository.save(investmentAccount);


        Transactions depositTransaction = mapper.readValue(CommonUtilities.getFileContent("profile1/depositTransaction1.json"), Transactions.class);
        transactionsRepository.saveAll(depositTransaction.getTransactions());

        Transactions loanTransaction = mapper.readValue(CommonUtilities.getFileContent("profile1/loanTransaction1.json"), Transactions.class);
        transactionsRepository.saveAll(loanTransaction.getTransactions());


        List<InvestmentTransaction> investmentTransaction = mapper.readValue(CommonUtilities.getFileContent("profile1/investmentTransaction1.json"),  new TypeReference<List<InvestmentTransaction>>() {});
        transactionsRepository.saveAll(investmentTransaction);

        AccountContact depositContact = mapper.readValue(CommonUtilities.getFileContent("profile1/deposit_contact_details1.json"), AccountContact.class);
        contactRepository.save(depositContact);

        AccountContact loanContact = mapper.readValue(CommonUtilities.getFileContent("profile1/loan_contact_details1.json"), AccountContact.class);
        contactRepository.save(loanContact);

        AccountContact investmentContact = mapper.readValue(CommonUtilities.getFileContent("profile1/investment_contact_details1.json"), AccountContact.class);
        contactRepository.save(investmentContact);



        log.info("Profile1 save successfully");
    }

    private void profile2Created(DepositAccountsRepository depositAccountsRepository,
                                 LoanAccountsRepository loanAccountsRepository,
                                 TransactionsRepository transactionsRepository, ContactRepository contactRepository,
                                 PaymentNetworksRepository paymentNetworksRepository, AccountConsentRepository consentRepository,
                                 FdxUserRepository fdxUserRepository, ObjectMapper mapper)
            throws JsonProcessingException, JsonMappingException {
        // 1. Create user
        FdxUser fdxUser = new FdxUser(0, "fdxuser2", CommonUtilities.encrypthash(pwd),null);
        fdxUserRepository.save(fdxUser);

        //2. saving account for profile of fdxuser1...
        AccountConsent accountConsent = new AccountConsent(0,"fdxuser2", Arrays.asList("789055","600011"),null,0,"");
        consentRepository.save(accountConsent);

        LoanAccount loanAccounts = mapper.readValue(CommonUtilities.getFileContent("profile2/loan_acc_details2.json"), LoanAccount.class);
        loanAccountsRepository.save(loanAccounts);

        DepositAccount depositAccount = mapper.readValue(CommonUtilities.getFileContent("profile2/deposit_acc_details2.json"), DepositAccount.class);
        depositAccountsRepository.save(depositAccount);


        Transactions depositTransaction = mapper.readValue(CommonUtilities.getFileContent("profile2/depositTransaction2.json"), Transactions.class);
        transactionsRepository.saveAll(depositTransaction.getTransactions());

        Transactions loanTransaction = mapper.readValue(CommonUtilities.getFileContent("profile2/loanTransaction2.json"), Transactions.class);
        transactionsRepository.saveAll(loanTransaction.getTransactions());

        AccountContact depositContact = mapper.readValue(CommonUtilities.getFileContent("profile2/deposit_contact_details2.json"), AccountContact.class);
        contactRepository.save(depositContact);

        AccountContact loanContact = mapper.readValue(CommonUtilities.getFileContent("profile2/loan_contact_details2.json"), AccountContact.class);
        contactRepository.save(loanContact);


        log.info("Profile2 save successfully");
    }
    
    private void profile3Created(DepositAccountsRepository depositAccountsRepository,
			LoanAccountsRepository loanAccountsRepository, AccountConsentRepository consentRepository,
			FdxUserRepository fdxUserRepository, ObjectMapper mapper) throws JsonMappingException, JsonProcessingException {
    	
    	 // 1. Create user
        FdxUser fdxUser = new FdxUser(0, "fdxuser3", CommonUtilities.encrypthash(pwd),null);
        fdxUserRepository.save(fdxUser);

        //2. saving account for profile of fdxuser3 with all closed accounts...
        AccountConsent accountConsent = new AccountConsent(0,"fdxuser3", Arrays.asList("450022","4023555"),null,0,"");
        consentRepository.save(accountConsent);

        LoanAccount loanAccounts = mapper.readValue(CommonUtilities.getFileContent("profile3/loan_acc_details3.json"), LoanAccount.class);
        loanAccountsRepository.save(loanAccounts);

        DepositAccount depositAccount = mapper.readValue(CommonUtilities.getFileContent("profile3/deposit_acc_details3.json"), DepositAccount.class);
        depositAccountsRepository.save(depositAccount);

    	
	}
}
