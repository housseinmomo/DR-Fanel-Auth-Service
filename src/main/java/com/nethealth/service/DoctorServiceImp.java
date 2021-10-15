package com.nethealth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.nethealth.entities.Doctor;
import com.nethealth.entities.Role;
import com.nethealth.repository.DoctorRepository;


@Service
@Validated
public class DoctorServiceImp implements DoctorService{

	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	BCryptPasswordEncoder bcrypPasswordEncoder;
	
	@Override
	public Doctor saveDoctor(Doctor doctor) {
		String password = doctor.getPerson().getPassword();
		doctor.getPerson().setPassword(bcrypPasswordEncoder.encode(password));
		doctor.getPerson().setStatus(0);
		List<String> roles = new ArrayList<>();
		roles.add(Role.DOCTOR.name());
		doctor.getPerson().setRoles(roles);
		return doctorRepository.save(doctor);
	}
	
	
	@Override
	public Doctor updateDoctor(Doctor doctor) {
		return doctorRepository.save(doctor);
	}
	
	@Override
	public Doctor updatePasswordDoctor(Doctor doctor, String password) {
		doctor.getPerson().setPassword(bcrypPasswordEncoder.encode(password));
		return doctorRepository.save(doctor);
	}
	
	
	@Override
	public Doctor findDoctorByUsername(String username) {
		return doctorRepository.findByPersonUsername(username);
	}
	
	@Override
	public Doctor findDoctorByEmail(String email) {
		return doctorRepository.findByPersonEmail(email);
	}
	
	
	
	
}
