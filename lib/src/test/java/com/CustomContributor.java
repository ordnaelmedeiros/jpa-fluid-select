package com;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomContributor implements MetadataBuilderContributor {

	@Override
	public void contribute(MetadataBuilder metadataBuilder) {
		
		metadataBuilder.applySqlFunction(
	            "to_char",
	            new StandardSQLFunction(
	                "to_char",
	                StandardBasicTypes.STRING
	            )
	        );
		
	}
	
}
