package com.mastercard.fdx.mock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.fdx.mock.entity.FdxUser;
import com.mastercard.fdx.mock.service.FdxUserService;

@RestController
@RequestMapping("/user")
public class FdxUserDetailsController {

	@Autowired
	FdxUserService fdxUserService;
	
	  
	@GetMapping("/{userId}")
	public ResponseEntity<FdxUser> getUser(@RequestHeader(value = "Authorization", required = false) String authorization ,
										   @PathVariable String userId) {
		return fdxUserService.getUser(userId);
	}
	
	@PostMapping("")
	public ResponseEntity<FdxUser> saveUser(@RequestHeader(value = "Authorization", required = false) String authorization,
											@RequestBody FdxUser fdxUser) {
		 return fdxUserService.saveUser(fdxUser);
	}
	
	

}
