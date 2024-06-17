package com.mastercard.fdx.mock.entity;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mock_statement")
public class Statement {
		

	@JsonIgnore
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")	
	private int id;
	
	private String accountId;
	private String statementId;
	private Calendar statementDate;
	private String description;
	private String status;
	
	@JsonIgnore
	@Lob
	private byte[] file;
}
