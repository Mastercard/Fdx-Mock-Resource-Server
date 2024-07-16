package com.mastercard.fdx.mock.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mastercard.fdx.mock.repository.FdxUserRepository;
import com.mastercard.fdx.mock.entity.FdxUser;
import com.mastercard.fdx.mock.utilities.CommonUtilities;

@Service
public class FdxUserService {

	@Autowired
	FdxUserRepository fdxUserRepository;
	
	public ResponseEntity<FdxUser> getUser(String userId){
		FdxUser fdxUser =  fdxUserRepository.findByUserId(userId);
		if(Objects.isNull(fdxUser)) {
			FdxUser fdxUser2 = new FdxUser();
		  	fdxUser2.setErrorCode(1002);
		  	fdxUser2.setErrorMessage("User not found");
			return new ResponseEntity<>(fdxUser2, HttpStatus.NOT_FOUND);
		}
		return  new ResponseEntity<>(fdxUser,HttpStatus.OK);
	}
	
	public ResponseEntity<FdxUser> saveUser(FdxUser fdxUser) {
		  if(fdxUserRepository.findByUserId(fdxUser.getUserId()) != null) {
			  	FdxUser fdxUser2 = new FdxUser();
			  	fdxUser2.setErrorCode(1001);
			  	fdxUser2.setErrorMessage("User Already Exists");
	            return new ResponseEntity<>(fdxUser2,HttpStatus.CONFLICT);
	        }
		fdxUser.setPasswordHash(CommonUtilities.encrypthash(fdxUser.getPassword()));
		return new ResponseEntity<>(fdxUserRepository.save(fdxUser),HttpStatus.OK);
	}
}
