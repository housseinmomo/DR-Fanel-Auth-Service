package com.nethealth.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
@Document(collection = "establishments")
public class Establishment {
	@Id
	private String id;
	private String name;
	private Date startDate;
	private Date endDate;
	private String phoneNumber;
	private Location location;

}