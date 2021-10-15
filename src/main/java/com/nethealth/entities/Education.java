package com.nethealth.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
@Document(collection = "trainings")
public class Education {
	@Id
	private String id;
	private String degree;
	private Establishment establishment;

}
