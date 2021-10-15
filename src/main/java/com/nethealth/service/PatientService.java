package com.nethealth.service;

import com.nethealth.entities.Patient;

public interface PatientService {
	
	Patient savePatient(Patient patient);
	Patient findPatientByUsername(String username);
	Patient findPatientByEmail(String email);
	public Patient updatePatient(Patient patient);
	public Patient updatePasswordPatient(Patient patient, String password);

}
