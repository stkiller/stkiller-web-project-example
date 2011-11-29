package com.example.bl.datavalidation.implementation;

import com.example.bl.datavalidation.interfaces.IValidationResult;

public class ValidationResult implements IValidationResult {
	private boolean valid;
	private String message;
	
	public ValidationResult(){}
	
	public ValidationResult(boolean valid, String message) {
		super();
		this.valid = valid;
		this.message = message;
	}
	@Override
	public String getValidationResultMessage() {
		return message;
	}
	@Override
	public boolean isValid() {
		return valid;
	}
	@Override
	public void setValid(boolean valid) {
		this.valid=valid;
	}
	
	@Override
	public void setValidationResultMessage(String message) {
		this.message=message;
	}
	
	

}
