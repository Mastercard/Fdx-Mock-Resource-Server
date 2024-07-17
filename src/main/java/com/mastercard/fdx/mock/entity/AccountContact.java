package com.mastercard.fdx.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mastercard.fdx.mock.dto.DeliveryAddress;
import com.mastercard.fdx.mock.dto.ErrorPojo;
import com.mastercard.fdx.mock.dto.TelephoneNumber;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "mock_contacts")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Inheritance(strategy = InheritanceType.JOINED)
public class AccountContact extends ErrorPojo implements Serializable {

	@Serial
	private static final long serialVersionUID = -8407347710587958599L;

	@JsonIgnore
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")	
	private int id;
	
	private String accountId;
	
	@ElementCollection  
	private List<AccountHolders> holders;
	 
	@JsonProperty("emails")
	@JdbcTypeCode(SqlTypes.JSON)
	private List<String> emails;
			
	@JsonProperty("addresses") 
	@JdbcTypeCode(SqlTypes.JSON)
	private List<DeliveryAddress> addresses;
		 
		
	@JsonProperty("telephones")
	@JdbcTypeCode(SqlTypes.JSON)
	private List<TelephoneNumber> telephones;


	public AccountContact() {
		super();
	}

	public AccountContact(Integer errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

}
