package com.rabobank.statement;

import java.io.File;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.statement.constants.PortalConstants;
import com.rabobank.statement.model.AppResponse;
import com.rabobank.statement.service.StatementProcessor;

@RestController
@RequestMapping("/rabobank")
public class StatementController {

	@Autowired
	private StatementProcessor statementProcessor;

	@RequestMapping(value = "/health", method = RequestMethod.GET)
	public String healthCheck() {
		return "success";
	}

	@RequestMapping(value = "/processStatment", method = RequestMethod.POST)
	public AppResponse handleFileUpload(@RequestParam("file") MultipartFile multipart) throws Exception {
		AppResponse appResponse = new AppResponse();
		if (!multipart.isEmpty()) {
			if (multipart.getContentType().equalsIgnoreCase(PortalConstants.FILE_TYPE_CSV)) {
				File csvFile = new File(Paths.get("").toAbsolutePath() + "\\" + multipart.getOriginalFilename());
				multipart.transferTo(csvFile);
				statementProcessor.process(appResponse, csvFile);
			} else if (multipart.getContentType().equalsIgnoreCase(PortalConstants.FILE_TYPE_XML)) {
				File xmlFile = new File(Paths.get("").toAbsolutePath() + "\\" + multipart.getOriginalFilename());
				multipart.transferTo(xmlFile);
				statementProcessor.process(appResponse, xmlFile);
			} else {
				appResponse.setResponseCode(PortalConstants.HTTP_CODE_INVALID_INPUT);
				appResponse.setResponseMessage(PortalConstants.UNSUPORTED_FILE_FORMAT);
			}
		} else {
			appResponse.setResponseCode(PortalConstants.HTTP_CODE_INVALID_INPUT);
			appResponse.setResponseMessage(PortalConstants.INVALID_INPUT);
		}
		return appResponse;
	}


	@ExceptionHandler(Exception.class)
	public AppResponse handleException(HttpServletRequest request, Exception ex) {
		AppResponse appResponse = new AppResponse();
		appResponse.setResponseCode(PortalConstants.HTTP_CODE_ERROR);
		appResponse.setResponseMessage(ex.getMessage());
		return appResponse;
	}

}
