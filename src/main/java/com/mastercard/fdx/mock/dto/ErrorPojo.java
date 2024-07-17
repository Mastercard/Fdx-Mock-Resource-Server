package com.mastercard.fdx.mock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ErrorPojo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private Integer errorCode;
	
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private String errorMessage;
}
