package com.rabobank.statement.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rabobank.statement.model.Record;


public interface ExtractorService {
	public List<Record> extractStatmentFromCSV(File file) throws Exception;

	public List<Record> extractStatmentFromXML(File file) throws Exception;
}
