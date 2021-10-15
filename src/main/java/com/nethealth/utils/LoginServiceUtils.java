package com.nethealth.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.nethealth.dtos.LoginDto;
import com.nethealth.entities.Doctor;
import com.nethealth.entities.Patient;
import com.nethealth.repository.DoctorRepository;
import com.nethealth.repository.PatientRepository;

@Component
public class LoginServiceUtils {
	
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PatientRepository patientRepository;
	
	
	public LoginDto createUserDetail(String username) {
		LoginDto loginModel = null;
		if(doctorRepository.findByPersonUsername(username) != null) {
			Doctor doctor = doctorRepository.findByPersonUsername(username);
			String login = doctor.getPerson().getUsername();
			String password = doctor.getPerson().getPassword();
			loginModel = new LoginDto(login, password);
		}
		else if(doctorRepository.findByPersonEmail(username) != null) {
			Doctor doctor = doctorRepository.findByPersonEmail(username);
			String login = doctor.getPerson().getEmail();
			String password = doctor.getPerson().getPassword();
			loginModel = new LoginDto(login, password);
		}
		else if(patientRepository.findByPersonUsername(username) != null) {
			Patient patient = patientRepository.findByPersonUsername(username);
			String login = patient.getPerson().getUsername();
			String password = patient.getPerson().getPassword();
			loginModel = new LoginDto(login, password);
		}
		else if(patientRepository.findByPersonEmail(username) != null) {
			Patient patient = patientRepository.findByPersonEmail(username);
			String login = patient.getPerson().getEmail();
			String password = patient.getPerson().getPassword();
			loginModel = new LoginDto(login, password);
		}
		return loginModel;
		
	}
	
	
	
	public List<GrantedAuthority> addAuthority (String username) {
		List<GrantedAuthority> grantedAuthority = new ArrayList<>();
		if(doctorRepository.findByPersonUsername(username) != null) {
			doctorRepository.findByPersonUsername(username).getPerson().getRoles().forEach(role ->{
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantedAuthority.add(authority);
			});
		}
		else if(doctorRepository.findByPersonEmail(username) != null) {
			doctorRepository.findByPersonEmail(username).getPerson().getRoles().forEach(role ->{
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantedAuthority.add(authority);
			});
		}else if(patientRepository.findByPersonUsername(username) != null) {
			patientRepository.findByPersonUsername(username).getPerson().getRoles().forEach(role ->{
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantedAuthority.add(authority);
			});
		}else if(patientRepository.findByPersonEmail(username)!= null) {
			patientRepository.findByPersonEmail(username) .getPerson().getRoles().forEach(role ->{
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantedAuthority.add(authority);
			});
		}
		return grantedAuthority;
		
	}
	
	
	public boolean fetchStatus(String username) {
		int status = 0;
		if(doctorRepository.findByPersonUsername(username) != null) {
			Doctor doctor = doctorRepository.findByPersonUsername(username);
			status = doctor.getPerson().getStatus();
		}
		else if(doctorRepository.findByPersonEmail(username) != null) {
			Doctor doctor = doctorRepository.findByPersonEmail(username);
			status = doctor.getPerson().getStatus();
		}
		else if(patientRepository.findByPersonUsername(username) != null) {
			Patient patient = patientRepository.findByPersonUsername(username);
			status = patient.getPerson().getStatus();
		}
		else if(patientRepository.findByPersonEmail(username) != null) {
			Patient patient = patientRepository.findByPersonEmail(username);
			status = patient.getPerson().getStatus();
		}
		return status != 0 ? true : false;
	}
	
	
	
	

}
