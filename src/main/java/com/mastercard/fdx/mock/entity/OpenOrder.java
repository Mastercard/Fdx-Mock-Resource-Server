package com.mastercard.fdx.mock.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mastercard.fdx.mock.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;

@Getter
@Setter
@Entity
@JsonIgnoreProperties(ignoreUnknown =  true)
@JsonInclude(Include.NON_NULL)
public class OpenOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_Id")
	private int orderId;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private InvestmentAccount investmentAccountId;

	private String securityId;

	private SecurityIdType securityIdType;

	private String symbol;
	private String description;
	private double units;

	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	private Calendar orderDate;
	private Double unitPrice;
	private UnitType unitType;
	private OrderDuration orderDuration;
	private SubAccountType subAccount;
	private Double limitPrice;
	private Double stopPrice;
	private Inv401kSource inv401kSource;
}
