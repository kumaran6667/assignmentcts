package com.rabobank.statement.service;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.rabobank.statement.model.Record;
import com.rabobank.statement.model.Records;

/**
 * 
 * class to extract the uploaded file and convert to list of record java object.
 */
@Service
public class ExtractorServiceImpl implements ExtractorService {
	/**
	 * @return List<Records>
	 */
	public List<Record> extractStatmentFromCSV(File file) throws Exception{
		HeaderColumnNameTranslateMappingStrategy<Record> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<Record>();
		beanStrategy.setType(Record.class);
		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("Reference", "transactionRef");
		columnMapping.put("AccountNumber", "accountNumber");
		columnMapping.put("Description", "description");
		columnMapping.put("Start Balance", "startBalance");
		columnMapping.put("Mutation", "mutation");
		columnMapping.put("End Balance", "endBalance");
		beanStrategy.setColumnMapping(columnMapping);
		CsvToBean<Record> csvToBean = new CsvToBean<Record>();
		CSVReader reader;
		List<Record> records = null;
		try {
			reader = new CSVReader(new FileReader(file));
			csvToBean.setCsvReader(reader);
			csvToBean.setMappingStrategy(beanStrategy);
			records = csvToBean.parse();
		} catch (Exception e) {
			throw new Exception("Error while parsing csv file",e);
		} 
		return records;
	}

	/**
	 * @return List<Records>
	 */
	public List<Record> extractStatmentFromXML(File file) throws Exception{

		JAXBContext jaxbContext;
		Records rootRecord = null;
		try {
			jaxbContext = JAXBContext.newInstance(Records.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			rootRecord = (Records) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new Exception("Error while parsing xml file",e);
		}
		
		return rootRecord.getRecord();
	}
}
