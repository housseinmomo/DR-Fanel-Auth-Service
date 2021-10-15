package com.nethealth.entities;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor @AllArgsConstructor @Data
@Document(collection = "persons")
public class Person {
	@Id
	private String id;
	@NotBlank
	@NotNull
	private String username;
	@NotBlank
	@NotNull
	@Pattern(regexp ="([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})")
	private String email;
	@NotBlank
	@NotNull
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")
	private String password;
	@NotBlank
	@NotNull
	private String firstname;
	@NotBlank
	@NotNull
	private String lastname;
	private Date dateOfBith;
	private String gender;
	@NotBlank
	@NotNull
	private String phoneNumber;
	private Location location;
	private List<String> roles;
	private int status;
	

}
