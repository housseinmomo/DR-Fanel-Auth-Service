package com.nethealth.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
@Document(collection = "work_locations")
public class WorkLocation {
	@Id
	private String id;
	private Establishment establishment;
	private TypeOfWorkLocation typeOfWork;

}
