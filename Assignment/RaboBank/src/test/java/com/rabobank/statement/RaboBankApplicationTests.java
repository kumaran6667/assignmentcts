package com.rabobank.statement;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.rabobank.statement.model.AppResponse;
import com.rabobank.statement.model.Record;
import com.rabobank.statement.service.ExtractorService;
import com.rabobank.statement.service.ExtractorServiceImpl;
import com.rabobank.statement.service.StatementProcessor;
import com.rabobank.statement.service.StatementProcessorImpl;
import com.rabobank.statement.service.ValidatorService;
import com.rabobank.statement.service.ValidatorServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RaboBankApplicationTests {

	@Mock
	private ValidatorService validatorService = new ValidatorServiceImpl();

	@Mock
	private ExtractorService extractorService = new ExtractorServiceImpl();

	@InjectMocks
	StatementProcessor processor = new StatementProcessorImpl();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testextractStatmentFromCSV() {
		try {
			Resource resource = new ClassPathResource("records.csv");
			File csvFile = resource.getFile();
//			Mockito.when(extractorServicess.extractStatmentFromCSV(new File("records.csv"))).thenReturn(records);
			ExtractorService extractorService = new ExtractorServiceImpl();
			System.err.println("extract" + extractorService.extractStatmentFromCSV(csvFile));
			Assert.assertEquals(RaboBankApplicationTests.getRecords(),
					extractorService.extractStatmentFromCSV(csvFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testextractStatmentFromXML() {
		try {
			Resource resource = new ClassPathResource("records.xml");
			File csvFile = resource.getFile();
			ExtractorService extractorService = new ExtractorServiceImpl();
			System.err.println("extract" + extractorService.extractStatmentFromXML(csvFile));
			Assert.assertNotEquals(RaboBankApplicationTests.getRecords(),
					extractorService.extractStatmentFromXML(csvFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testHandleFileUpload() {
		try {
			Resource resource = new ClassPathResource("records.csv");
			File csvFile = resource.getFile();
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
			reader = new CSVReader(new FileReader(csvFile));
			csvToBean.setCsvReader(reader);
			csvToBean.setMappingStrategy(beanStrategy);
			records = csvToBean.parse();
			AppResponse appResponse = new AppResponse();
			ExtractorService extractorServicess = Mockito.mock(ExtractorServiceImpl.class);
			Mockito.when(extractorServicess.extractStatmentFromCSV(new File("records.csv"))).thenReturn(records);
			processor.process(appResponse, csvFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testgetDuplicateRecords() throws IOException {
		ValidatorService validatorService = new ValidatorServiceImpl();
		validatorService.getDuplicateRecords(getRecords());
	}
	
	@Test
	public void getEndBalanceErrorRecords() throws IOException {
		ValidatorService validatorService = new ValidatorServiceImpl();
		validatorService.getEndBalanceErrorRecords(getRecords());
	}

	static List<Record> getRecords() throws IOException {
		Resource resource = new ClassPathResource("records.csv");
		File csvFile = resource.getFile();
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
		reader = new CSVReader(new FileReader(csvFile));
		csvToBean.setCsvReader(reader);
		csvToBean.setMappingStrategy(beanStrategy);
		return records = csvToBean.parse();
	}

}
