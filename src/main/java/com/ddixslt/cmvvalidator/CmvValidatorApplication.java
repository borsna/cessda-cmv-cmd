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
		if(args.length == 2){
			validateUsingFiles(args[0], args[1], ValidationGateName.BASIC);
		}
		else if(args.length == 3){
			validateUsingFiles(args[0], args[1], getValidationGate(args[2]));
		}else{
			System.out.println("arguments expected: /path/to/ddi-file.xml /path/to/profile.xml [validationGate]");
			System.out.println("suported values for validationGate: BASIC, BASICPLUS, EXTENDED, STANDARD, STRICT");
		}
	}

	private static void validateUsingFiles(String ddiFile, String profileFile, ValidationGateName validationGate)
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

	private static ValidationGateName getValidationGate(String value)
	{
		switch(value){
			case "BASICPLUS": return ValidationGateName.BASICPLUS;
			case "EXTENDED": return ValidationGateName.EXTENDED;
			case "STANDARD": return ValidationGateName.STANDARD;
			case "STRICT": return ValidationGateName.STRICT;
		}

		return ValidationGateName.BASIC;
	}

}
