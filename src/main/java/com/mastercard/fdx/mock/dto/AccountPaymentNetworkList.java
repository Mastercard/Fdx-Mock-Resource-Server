package com.mastercard.fdx.mock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mastercard.fdx.mock.entity.AccountPaymentNetwork;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class AccountPaymentNetworkList extends ErrorPojo implements Serializable {

	@Serial
	private static final long serialVersionUID = -7175380592347384203L;

	private Page page;
	private List<AccountPaymentNetwork> paymentNetworks;
	public AccountPaymentNetworkList() {
		super();
	}
	public AccountPaymentNetworkList(Integer errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

}

