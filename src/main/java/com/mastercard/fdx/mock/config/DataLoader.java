package com.mastercard.fdx.mock.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.fdx.mock.entity.*;
import com.mastercard.fdx.mock.repository.*;
import com.mastercard.fdx.mock.transaction.dto.InvestmentTransaction;
import com.mastercard.fdx.mock.transaction.dto.Transactions;
import com.mastercard.fdx.mock.utilities.CommonUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
public class DataLoader {


    @Bean
    CommandLineRunner runner(AccountsRepository accountsRepository,
                             DepositAccountsRepository depositAccountsRepository,
                             LoanAccountsRepository loanAccountsRepository, InvestmentAccountsRepository investmentAccountsRepository,
                             LineOfCreditAccountsRepository lineOfCreditAccountsRepository,
                             TransactionsRepository transactionsRepository, ContactRepository contactRepository,
                             PaymentNetworksRepository paymentNetworksRepository, AccountConsentRepository consentRepository, FdxUserRepository fdxUserRepository) {
        return args -> {
            // read json and write to db
            ObjectMapper mapper = new ObjectMapper();
            try {
                profileCreated(depositAccountsRepository, loanAccountsRepository, investmentAccountsRepository,
                        lineOfCreditAccountsRepository, transactionsRepository, contactRepository,
                        paymentNetworksRepository, consentRepository, fdxUserRepository, mapper);

                profile1Created(depositAccountsRepository, loanAccountsRepository, investmentAccountsRepository,
                        transactionsRepository, contactRepository,
                        paymentNetworksRepository, consentRepository, fdxUserRepository, mapper);

                profile2Created(depositAccountsRepository, loanAccountsRepository,
                        transactionsRepository, contactRepository,
                        paymentNetworksRepository, consentRepository, fdxUserRepository, mapper);


                log.info("All Profile and accout save successfully");
            } catch (Exception e){
                log.error("Unable to save profile and account resposne: " + e.getMessage());
            }
        };
    }

    private void profileCreated(DepositAccountsRepository depositAccountsRepository,
                                LoanAccountsRepository loanAccountsRepository, InvestmentAccountsRepository investmentAccountsRepository,
                                LineOfCreditAccountsRepository lineOfCreditAccountsRepository,
                                TransactionsRepository transactionsRepository, ContactRepository contactRepository,
                                PaymentNetworksRepository paymentNetworksRepository, AccountConsentRepository consentRepository,
                                FdxUserRepository fdxUserRepository, ObjectMapper mapper)
            throws JsonProcessingException, JsonMappingException {
        // 1. Create user
        FdxUser fdxUser = new FdxUser(1, "fdxuser", CommonUtilities.encrypthash("password"),null);
        fdxUserRepository.save(fdxUser);

        //2. saving account for profile of fdxuser...
        AccountConsent accountConsent = new AccountConsent(1,"fdxuser", Arrays.asList("10001","20001","3746272","5326884"),null,0,"");
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

        log.info("Profile save successfully");
    }

    private void profile1Created(DepositAccountsRepository depositAccountsRepository,
                                 LoanAccountsRepository loanAccountsRepository, InvestmentAccountsRepository investmentAccountsRepository,
                                 TransactionsRepository transactionsRepository, ContactRepository contactRepository,
                                 PaymentNetworksRepository paymentNetworksRepository, AccountConsentRepository consentRepository,
                                 FdxUserRepository fdxUserRepository, ObjectMapper mapper)
            throws JsonProcessingException, JsonMappingException {
        // 1. Create user
        FdxUser fdxUser = new FdxUser(2, "fdxuser1", CommonUtilities.encrypthash("password"),null);
        fdxUserRepository.save(fdxUser);

        //2. saving account for profile of fdxuser1...
        AccountConsent accountConsent = new AccountConsent(2,"fdxuser1", Arrays.asList("6468990","4568922","500011"),null,0,"");
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
        FdxUser fdxUser = new FdxUser(3, "fdxuser2", CommonUtilities.encrypthash("password"),null);
        fdxUserRepository.save(fdxUser);

        //2. saving account for profile of fdxuser1...
        AccountConsent accountConsent = new AccountConsent(3,"fdxuser2", Arrays.asList("789055","600011"),null,0,"");
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
}
