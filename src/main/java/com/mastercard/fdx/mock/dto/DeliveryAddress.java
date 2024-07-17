package com.mastercard.fdx.mock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryAddress extends AccountOwnerAddress implements Serializable {

	@Serial
	private static final long serialVersionUID = -7400901366450089454L;

	@JsonProperty("type")
	private DeliveryAddressTypePriority type;
}
