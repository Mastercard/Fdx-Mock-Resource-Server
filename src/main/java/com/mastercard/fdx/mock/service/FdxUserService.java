package com.mastercard.fdx.mock.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mastercard.fdx.mock.dto.FdxUserRequestDTO;
import com.mastercard.fdx.mock.dto.FdxUserResponseDTO;
import com.mastercard.fdx.mock.entity.FdxUser;
import com.mastercard.fdx.mock.repository.FdxUserRepository;
import com.mastercard.fdx.mock.utilities.CommonUtilities;

@Service
public class FdxUserService {

	@Autowired
	FdxUserRepository fdxUserRepository;
	
	public ResponseEntity<FdxUserResponseDTO> getUser(String userId){
		FdxUser fdxUser =  fdxUserRepository.findByUserId(userId);
		
		if(Objects.isNull(fdxUser)) {
			FdxUserResponseDTO fdxUser2 = new FdxUserResponseDTO();
		  	fdxUser2.setErrorCode(1002);
		  	fdxUser2.setErrorMessage("User not found");
			return new ResponseEntity<>(fdxUser2, HttpStatus.NOT_FOUND);
		}
		FdxUserResponseDTO res = new FdxUserResponseDTO();
		res.setUserId(fdxUser.getUserId());
		res.setPasswordHash(fdxUser.getPasswordHash());
		return  new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	public ResponseEntity<FdxUserResponseDTO> saveUser(FdxUserRequestDTO fdxUserRequestDTO) {
		  if(fdxUserRepository.findByUserId(fdxUserRequestDTO.getUserId()) != null) {
			  FdxUserResponseDTO fdxUser2 = new FdxUserResponseDTO();
			  	fdxUser2.setErrorCode(1001);
			  	fdxUser2.setErrorMessage("User Already Exists");
	            return new ResponseEntity<>(fdxUser2,HttpStatus.CONFLICT);
	        }
		 FdxUser fdxUser = new FdxUser();
		 fdxUser.setUserId(fdxUserRequestDTO.getUserId());
		 fdxUser.setPasswordHash(CommonUtilities.encrypthash(fdxUserRequestDTO.getPassword()));
		 
		FdxUser saveEntity =  fdxUserRepository.save(fdxUser);
		return new ResponseEntity<>(new FdxUserResponseDTO(saveEntity.getUserId(),saveEntity.getPasswordHash()),HttpStatus.OK);
	}
}
