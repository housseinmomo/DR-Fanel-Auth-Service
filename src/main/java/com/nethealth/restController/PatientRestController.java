package com.nethealth.restController;

import javax.security.auth.login.CredentialExpiredException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nethealth.entities.ConfirmationToken;
import com.nethealth.entities.Patient;
import com.nethealth.repository.ConfirmationTokenRepository;
import com.nethealth.repository.DoctorRepository;
import com.nethealth.service.PatientService;
import com.nethealth.utils.ResponseMessage;
import com.nethealth.utils.SendEmail;
import com.nethealth.utils.TelephoneNumberValidator;
import com.nethealth.utils.ValidateData;
import com.nethealth.utils.exception.ConditionException;
import com.nethealth.utils.exception.ObjectDataBaseException;
import com.nethealth.utils.exception.ObjectTokenException;


@RestController
@RequestMapping("/api/patients")
public class PatientRestController {
	
	@Autowired
	private PatientService patientService;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	private DoctorRepository doctorRepository;
	
	
	@PostMapping("")
	public ResponseEntity<Object> save(@RequestBody Patient patient) {
		ResponseMessage response = new ResponseMessage();
		ConfirmationToken confirmationToken = new ConfirmationToken(patient.getPerson().getEmail());
		String telephone = patient.getPerson().getPhoneNumber();
		String email = patient.getPerson().getEmail();
		String username = patient.getPerson().getUsername();
		try {
			ValidateData.validatorBean(patient.getPerson());
			ValidateData.validatorBean(patient.getPerson().getLocation());
			ValidateData.validatorCondition("telephone number invalid", TelephoneNumberValidator.validateTelephone(telephone));
			ValidateData.validatorObjectDataBase("a user already has this email", patientService.findPatientByEmail(email), doctorRepository.findByPersonEmail(email));
			ValidateData.validatorObjectDataBase("a user already has this username", patientService.findPatientByEmail(username), doctorRepository.findByPersonUsername(username));
			Patient savePatient = patientService.savePatient(patient);
			response.setStatus(HttpStatus.CREATED);
			response.addEntry("data",savePatient);
			confirmationTokenRepository.save(confirmationToken);
			javaMailSender.send(SendEmail.automaticEmail(email, confirmationToken.getConfirmationToken(), "Complete Registration!", "To confirm your account, please click here : "
			        +"http://localhost:9999/api/doctors/account/confirm?link="));	
			
		}catch (ConditionException | MailException | ValidationException exception ) {
			response.setStatus(HttpStatus.BAD_REQUEST);
            response.addEntry("message", exception.getMessage());
			
		}catch (ObjectDataBaseException exception) {
			response.setStatus(HttpStatus.CONFLICT);
            response.addEntry("message", exception.getMessage());
			
		}catch (Exception exception) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.addEntry("message", exception.getMessage());
		}
		
		return ResponseEntity.status(response.getStatus()).body(response.getResponse());
		
		
	}
	
	@GetMapping("/account/confirm")
	public ResponseEntity<Object> activateAccount(@RequestParam(value="link", required = true) String token){
		ResponseMessage response = new ResponseMessage();
		try {
			ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);
			ValidateData.validatorObjectToken("this link doesn't exist ", confirmationToken);
			confirmationToken.validatorTime("this link has expired");
			Patient registredPatient = patientService.findPatientByEmail(confirmationToken.getEmail());
			registredPatient.getPerson().setStatus(1);
			patientService.updatePatient(registredPatient);
			confirmationTokenRepository.delete(confirmationToken);
			response.setStatus(HttpStatus.ACCEPTED);
			response.addEntry("data",registredPatient);	
		}catch(CredentialExpiredException | ObjectTokenException exception) {
			response.setStatus(HttpStatus.BAD_REQUEST);
            response.addEntry("message : ", exception.getMessage());
		}catch (Exception exception) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.addEntry("message :  ", exception.getMessage());
		}
		return ResponseEntity.status(response.getStatus()).body(response.getResponse());
	}
	 
	
	
 

}
