package com.mastercard.fdx.mock.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Links implements Serializable {

	@Serial
	private static final long serialVersionUID = 171562790681289824L;

	private Next next;
}
