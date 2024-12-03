package com.mastercard.fdx.mock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FdxUserResponseDTO extends ErrorPojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private String userId;
	 private String passwordHash;
	

}
