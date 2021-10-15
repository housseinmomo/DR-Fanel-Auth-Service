package com.nethealth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nethealth.entities.Patient;
import com.nethealth.entities.Role;
import com.nethealth.repository.PatientRepository;

@Service
public class PatientServiceImp implements PatientService{
	
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	BCryptPasswordEncoder bcrypPasswordEncoder;
	
	
	@Override
	public Patient savePatient(Patient patient) {
		String password = patient.getPerson().getPassword();
		patient.getPerson().setPassword(bcrypPasswordEncoder.encode(password));
		patient.getPerson().setStatus(0);
		List<String> roles = new ArrayList<>();
		roles.add(Role.PATIENT.name());
		patient.getPerson().setRoles(roles);
		return patientRepository.save(patient);
		
	}
	
	@Override
	 public Patient findPatientByUsername(String username) {
		return patientRepository.findByPersonUsername(username);
	}
	
	@Override
	public Patient findPatientByEmail(String email) {
		return patientRepository.findByPersonEmail(email);
	}
	
	@Override
	public Patient updatePatient(Patient patient) {
		return patientRepository.save(patient);
	}
	
	@Override
	public Patient updatePasswordPatient(Patient patient, String password) {
		patient.getPerson().setPassword(bcrypPasswordEncoder.encode(password));
		return patientRepository.save(patient);
	}
	 

	 

}
