package com.example.bl.datavalidation.interfaces;

public interface IValidationResult {
	public void setValid(boolean valid);
	public boolean isValid();
	public String getValidationResultMessage();
	public void setValidationResultMessage(String message);
}
