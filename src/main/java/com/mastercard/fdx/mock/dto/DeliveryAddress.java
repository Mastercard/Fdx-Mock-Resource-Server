package com.mastercard.fdx.mock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryAddress extends AccountOwnerAddress {
	
	@JsonProperty("type")
	private DeliveryAddressTypePriority type;
}
