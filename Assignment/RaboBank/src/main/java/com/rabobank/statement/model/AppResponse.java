package com.rabobank.statement.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AppResponse {
	private String responseMessage;
	private int responseCode;
	private List<Record> duplicateRecords;
	private List<Record> endBalanceMismatchRecords;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public List<Record> getDuplicateRecords() {
		return duplicateRecords;
	}

	public void setDuplicateRecords(List<Record> duplicateRecords) {
		this.duplicateRecords = duplicateRecords;
	}

	public List<Record> getEndBalanceMismatchRecords() {
		return endBalanceMismatchRecords;
	}

	public void setEndBalanceMismatchRecords(List<Record> endBalanceMismatchRecords) {
		this.endBalanceMismatchRecords = endBalanceMismatchRecords;
	}

	@Override
	public String toString() {
		return "AppResponse [responseMessage=" + responseMessage + ", responseCode=" + responseCode
				+ ", duplicateRecords=" + duplicateRecords + ", endBalanceMismatchRecords=" + endBalanceMismatchRecords
				+ "]";
	}
	
	
	
}
