package com.rabobank.statement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rabobank.statement.model.Record;


public interface ValidatorService {
	public List<Record> getDuplicateRecords(List<Record> records);
	
	public List<Record> getEndBalanceErrorRecords(List<Record> records);
}
