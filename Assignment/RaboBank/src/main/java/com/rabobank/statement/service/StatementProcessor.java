package com.rabobank.statement.service;

import java.io.File;

import com.rabobank.statement.model.AppResponse;

public interface StatementProcessor {

	void process(AppResponse appResponse, File csvFile) throws Exception;

}
