package com.nethealth.restController;

import javax.security.auth.login.CredentialExpiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nethealth.dtos.LoginDto;
import com.nethealth.dtos.UserTokenDTO;
import com.nethealth.entities.ConfirmationToken;
import com.nethealth.repository.ConfirmationTokenRepository;
import com.nethealth.security.jwt.JwtUtils;
import com.nethealth.service.LoginService;
import com.nethealth.utils.LoginServiceUtils;
import com.nethealth.utils.PasswordUpdateUtils;
import com.nethealth.utils.PasswordValidation;
import com.nethealth.utils.ResponseMessage;
import com.nethealth.utils.SendEmail;
import com.nethealth.utils.ValidateData;
import com.nethealth.utils.exception.ConditionException;
import com.nethealth.utils.exception.ObjectTokenException;

@RestController
@RequestMapping("/api/users")
public class LoginRestController {
	
	@Autowired
	private LoginService loginModelService;
	@Autowired
	private BCryptPasswordEncoder bcrypPasswordEncoder;
	@Autowired
	private LoginServiceUtils loginServiceUtils;
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired 
	private PasswordUpdateUtils passwordUpdateUtils;

	
	@PostMapping("/login")
	public ResponseEntity<Object> siginIn(@RequestBody LoginDto loginDto){
		ResponseMessage response = new ResponseMessage();
		JwtUtils jwtUtils = new JwtUtils();
		try {
			User userDetailService = (User) loginModelService.loadUserByUsername(loginDto.getUsername());
			ValidateData.validatorCondition("the password is incorrect", bcrypPasswordEncoder.matches(loginDto.getPassword(), userDetailService.getPassword()));
			ValidateData.validatorCondition("this account is not activate", loginServiceUtils.fetchStatus(loginDto.getUsername()));
			String jwt = jwtUtils.generateToken(userDetailService);
			response.setStatus(HttpStatus.CREATED);
            response.addEntry("data", new UserTokenDTO(loginDto, jwt) );
		}catch (ConditionException | UsernameNotFoundException exception) {
			response.setStatus(HttpStatus.BAD_REQUEST);
            response.addEntry("message", exception.getMessage());
		}catch (Exception exception) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.addEntry("message", exception.getMessage());
		}
		return ResponseEntity.status(response.getStatus()).body(response.getResponse());
	}
	
	
	@PostMapping("/password/reset")
	public ResponseEntity<Object> resetPassword(@RequestParam(value="email", required = true) String email){
		ResponseMessage response = new ResponseMessage();
		LoginDto loginDto = loginServiceUtils.createUserDetail(email);
		try {
			ValidateData.validatorObjectToken("this email doesn't exist", loginDto);
			ConfirmationToken confirmationToken = new ConfirmationToken(loginDto.getUsername());
			confirmationTokenRepository.save(confirmationToken);
			javaMailSender.send(SendEmail.automaticEmail(email, confirmationToken.getConfirmationToken(), "Reset Password!","To rest password, please click here : "
			        +"http://localhost:9999/api/users/password?link="));
			response.setStatus(HttpStatus.OK);
            response.addEntry("data", confirmationToken);
		}catch(ObjectTokenException | MailException exception) {
			response.setStatus(HttpStatus.BAD_REQUEST);
            response.addEntry("message", exception.getMessage());
		} catch (Exception exception) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.addEntry("message", exception.getMessage());
		}
		return ResponseEntity.status(response.getStatus()).body(response.getResponse());
	}
	
	
	@PutMapping("/password")
	public ResponseEntity<Object> updatePassword(@RequestParam(value="link", required = true) String token, @RequestParam(value="password", required = true) String password){
		ResponseMessage response = new ResponseMessage();
		try {
			ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);
			ValidateData.validatorObjectToken("this link doesn't exist ", confirmationToken);
			confirmationToken.validatorTime("this link has expired");
			ValidateData.validatorCondition("The password must have at least 8 characters, one lower case letter, one upper case letter and at least one number.", PasswordValidation.validatePassword(password));
			Object updatePasswordUser = passwordUpdateUtils.updateUserPassword(password, confirmationToken);
			confirmationTokenRepository.delete(confirmationToken);
			response.setStatus(HttpStatus.ACCEPTED);
			response.addEntry("data", updatePasswordUser);	
		}catch(CredentialExpiredException | ObjectTokenException | ConditionException exception) {
			response.setStatus(HttpStatus.BAD_REQUEST);
            response.addEntry("message", exception.getMessage());
		} catch (Exception exception) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.addEntry("message", exception.getMessage());
		}
		return ResponseEntity.status(response.getStatus()).body(response.getResponse());
	}
	
	
	

}
 