package com.mastercard.fdx.mock.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountConsentResponse {

    private String customerId;

    private String consentId;

    private Timestamp endDate;
}
