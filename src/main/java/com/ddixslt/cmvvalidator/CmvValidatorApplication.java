package com.ddixslt.cmvvalidator;

import java.io.File;

import org.gesis.commons.resource.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import eu.cessda.cmv.core.CessdaMetadataValidatorFactory;
import eu.cessda.cmv.core.ValidationGateName;
import eu.cessda.cmv.core.ValidationService;
import eu.cessda.cmv.core.mediatype.validationreport.v0.ValidationReportV0;

@SpringBootApplication
public class CmvValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmvValidatorApplication.class, args);
		if(args.length != 2){
			System.out.println("arguments expected: ddi-file.xml profile.xml");
		}else{
			validateUsingFiles(args[0], args[1]);
		}
	}

	private static void validateUsingFiles(String ddiFile, String profileFile)
	{
		CessdaMetadataValidatorFactory factory = new CessdaMetadataValidatorFactory();
		ValidationService.V10 validationService = factory.newValidationService();
		Resource document = Resource.newResource(new File(ddiFile));
		Resource profile = Resource.newResource(new File(profileFile));
		
		ValidationReportV0 validationReport = validationService.validate(document, profile, ValidationGateName.BASIC);
		boolean isValid = validationReport.getConstraintViolations().isEmpty();
		if(isValid){
			System.exit(0);
		}else{
			validationReport.getConstraintViolations().forEach( cv -> System.out.println( cv.getMessage() ) );
			System.exit(1);
		}
	}

}
