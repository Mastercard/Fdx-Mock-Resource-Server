package com.mastercard.fdx.mock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mastercard.fdx.mock.dto.ErrorPojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user_details")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "User")
public class FdxUser{
	
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	@Id
	private int id;


	@ApiModelProperty(required = true, example = "user name")
    private String userId;
	

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String passwordHash;
    
    @ApiModelProperty(required = true, example = "password")
    @Transient
    private String password;
    
    
}
