package com.nethealth.restController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nethealth.dtos.LoginDto;
import com.nethealth.entities.ConfirmationToken;
import com.nethealth.repository.ConfirmationTokenRepository;
import com.nethealth.service.LoginService;
import com.nethealth.utils.LoginServiceUtils;
import com.nethealth.utils.PasswordUpdateUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginRestController.class)
class LoginRestControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	@MockBean
	LoginService loginModelService;
	@MockBean
	BCryptPasswordEncoder bcrypPasswordEncoder;
	@MockBean
	LoginServiceUtils loginServiceUtils;
	@MockBean
	private ConfirmationTokenRepository confirmationTokenRepository;
	@MockBean
	private JavaMailSender javaMailSender;
	@MockBean
	PasswordUpdateUtils passwordUpdateUtils;
	@Autowired
	private ObjectMapper objectMapper;
	
	static LoginDto loginDto1;
	static LoginDto loginDto2;
	static User user;
	static ConfirmationToken confirmationToken;
	
	@BeforeAll
    public static void setUp(){
		List<GrantedAuthority> grantedAuthority = new ArrayList<>();
		loginDto1 = new LoginDto("louise", "mamaLii123");
		user = new User("louise","mamaLii123", grantedAuthority); 
		confirmationToken = new ConfirmationToken("bbili@gmail.com");
		loginDto2 = new LoginDto("bbili@gmail.com", "mamaLii123");
    }

	

	@Test
	void testSiginIn() throws Exception {
		Mockito.when(loginModelService.loadUserByUsername(user.getUsername())).thenReturn(user);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto1));
		System.out.println(Mockito.when(loginModelService.loadUserByUsername(loginDto1.getUsername())).thenReturn(user).toString());
		System.out.println(objectMapper.writeValueAsString(loginDto1));
		mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().is4xxClientError());
	}
	
	
	@Test
	void testResetPassword() throws Exception {
		Mockito.when(loginServiceUtils.createUserDetail(loginDto2.getUsername())).thenReturn(loginDto2);
		Mockito.when(confirmationTokenRepository.save(confirmationToken)).thenReturn(confirmationToken);
		mockMvc.perform(post("/api/users/password/reset").param("email", "bbili@gmail.com")).andExpect(status().is2xxSuccessful());
	}
	
	
	@Test
	void testUpdatePassword() throws Exception{
		String token = confirmationToken.getConfirmationToken();
		Mockito.when(confirmationTokenRepository.findByConfirmationToken(token)).thenReturn(confirmationToken);
		mockMvc.perform(put("/api/users/password").param("link", token).param("password", "PassWord12345")).andExpect(status().is2xxSuccessful());
	}
	
	

}
