package com.nethealth.service;

import org.springframework.stereotype.Service;

import com.nethealth.entities.Doctor;

@Service
public interface DoctorService {
	
	public Doctor saveDoctor(Doctor doctor);
	public Doctor updateDoctor(Doctor doctor);
	public Doctor findDoctorByUsername(String username);
	public Doctor findDoctorByEmail(String email);
	public Doctor updatePasswordDoctor(Doctor doctor, String password);

}
