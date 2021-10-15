package com.nethealth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nethealth.entities.ConfirmationToken;
import com.nethealth.entities.Doctor;
import com.nethealth.entities.Patient;
import com.nethealth.service.DoctorService;
import com.nethealth.service.PatientService;

@Component
public class PasswordUpdateUtils {
	
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PatientService patientService;
	
	public Object updateUserPassword(String password, ConfirmationToken confirmationToken) {
		Doctor registeredDoctor = doctorService.findDoctorByEmail(confirmationToken.getEmail());
		Patient registredPatient = patientService.findPatientByEmail(confirmationToken.getEmail());
		Object object = null;
		if(registeredDoctor != null) {
		 	object = doctorService.updatePasswordDoctor(registeredDoctor, password);
		}
		if(registredPatient != null) {
			object = patientService.updatePasswordPatient(registredPatient, password);
		}
		return object;
	}

}
