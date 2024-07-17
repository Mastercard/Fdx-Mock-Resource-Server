package com.mastercard.fdx.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mastercard.fdx.mock.dto.SecurityId;
import com.mastercard.fdx.mock.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@JsonIgnoreProperties(ignoreUnknown =  true)
@JsonInclude(Include.NON_NULL)
public class Holding implements Serializable {

	@Serial
	private static final long serialVersionUID = -4854843150430195052L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holding_id")
	private int holding_id;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private InvestmentAccount investmentAccountId;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = InvestmentAccount.class)
	private List<SecurityId> securityIds;
	private String holdingName;
	@Enumerated(EnumType.STRING)
	private HoldingType holdingType;
	
	@Enumerated(EnumType.STRING)
	private HoldingSubType holdingSubType;
	
	@Enumerated(EnumType.STRING)
	private PositionType positionType;
	
	@Enumerated(EnumType.STRING)
	private HeldInAccount heldInAccount;
	
	private String description;
	private String symbol;
	private Date originalPurchaseDate;
	private double purchasedPrice;
	private double currentAmortizationFactor;
	private double currentUnitPrice;
	private double changeInPrice;
	private Date currentUnitPriceDate;
	private double units;
	private double marketValue;
	private double faceValue;
	private boolean averageCost;
	private boolean cashAccount;
	private double rate;
	private String expirationDate;
	private Inv401kSource inv401kSource;
	private String digitalUnits;
}
