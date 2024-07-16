package com.mastercard.fdx.mock.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Next implements Serializable {

	@Serial
	private static final long serialVersionUID = 3734375570963513519L;

	private String href;
}
