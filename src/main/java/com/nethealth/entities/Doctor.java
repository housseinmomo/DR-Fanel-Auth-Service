package com.nethealth.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor @AllArgsConstructor @Data
@Document(collection = "doctors")
public class Doctor {
	@Id
	private String id;
	private Person person;
	private WorkLocation location_name;
	private Education training;
	


}
