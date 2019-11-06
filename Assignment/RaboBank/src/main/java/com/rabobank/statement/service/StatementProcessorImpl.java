package com.rabobank.statement.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabobank.statement.constants.PortalConstants;
import com.rabobank.statement.model.AppResponse;
import com.rabobank.statement.model.Record;

@Service
public class StatementProcessorImpl implements StatementProcessor {

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private ExtractorService extractorService;
	
	@Override
	public void process(AppResponse appResponse, File csvFile) throws Exception {
		List<Record> extractedRecords = extractorService.extractStatmentFromCSV(csvFile);
		if (extractedRecords != null && extractedRecords.size() > 0) {
			appResponse.setDuplicateRecords(validatorService.getDuplicateRecords(extractedRecords));
			appResponse
					.setEndBalanceMismatchRecords(validatorService.getEndBalanceErrorRecords(extractedRecords));
			appResponse.setResponseCode(PortalConstants.HTTP_CODE_SUCCESS);
		} else {
			appResponse.setResponseCode(PortalConstants.HTTP_CODE_INVALID_INPUT);
			appResponse.setResponseMessage(PortalConstants.NO_RECORDS_IN_INPUT_FILE);
		}
	}
}
