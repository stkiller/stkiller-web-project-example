package com.example.bl.datavalidation.implementation;

import com.example.bl.datavalidation.interfaces.IValidationResult;
import com.example.bl.datavalidation.interfaces.IValidator;
import com.example.dal.valueobject.RoleVO;

public class RoleValidator implements IValidator<RoleVO> {

	@Override
	public IValidationResult isValid(RoleVO object) {
		if(object ==null ){
			return new ValidationResult(false, "Role object cannot be null");
		}
		if(object.getName()==null || object.getName().length()<5){
			return new ValidationResult(false, "Role name cannot be null or shorter than 5 chars");
		}
		return new ValidationResult(true, "");
	}

	@Override
	public IValidationResult isIdentityValid(RoleVO object) {
		if(object ==null ){
			return new ValidationResult(false, "Role object cannot be null");
		}
		if(object.getId()==null || object.getId()<1){
			return new ValidationResult(false, "Role id cannot be null or lesser that 1 :"+object.getId());
		}
		return new ValidationResult(true, "");
	}

}
