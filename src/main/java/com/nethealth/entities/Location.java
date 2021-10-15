package com.nethealth.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor @AllArgsConstructor @Data
@Document(collection = "locations")
public class Location {
	@Id
	private String id;
	@NotNull
	@NotBlank
	private String country;
	@NotNull
	@NotBlank
	private String district;
	private String address;
	private String postal_code;

}
