package com.nethealth.restController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nethealth.entities.ConfirmationToken;
import com.nethealth.entities.Doctor;
import com.nethealth.entities.Education;
import com.nethealth.entities.Location;
import com.nethealth.entities.Person;
import com.nethealth.entities.WorkLocation;
import com.nethealth.repository.ConfirmationTokenRepository;
import com.nethealth.repository.PatientRepository;
import com.nethealth.service.DoctorService;

@RunWith(SpringRunner.class)
@WebMvcTest(DoctorRestController.class)
class DoctorRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private DoctorService doctorService;
	@MockBean
	ConfirmationTokenRepository confirmationTokenRepository;
	@MockBean
	PatientRepository patientRepository;
	@MockBean
	private JavaMailSender javaMailSender;
	@Autowired
	private ObjectMapper objectMapper;
	
	static Doctor doctor;
	static ConfirmationToken confirmationToken;

	@BeforeAll
    public static void setUp(){
		List<String> roles = new ArrayList<>();
		doctor = new Doctor(null,
				new Person(null, "bbili", "elslouisette@gmail.com", "TTili123", "mimi", "mimi", new Date(), "female",
						"+221 47 78 95", new Location(null, "Senegal", "dakar", "point e", "98000"), roles, 0),
				 new WorkLocation(), new Education());
		confirmationToken = new ConfirmationToken("elslouisette@gmail.com");
    }


	@Test
	void testSave() throws Exception{
		Mockito.when(doctorService.saveDoctor(doctor)).thenReturn(doctor);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor));
		mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().is2xxSuccessful());
		
	}

	@Test
	void testActivateAccount() throws Exception {
		String token = confirmationToken.getConfirmationToken();
		String uri = "/api/doctors/account/confirm?link=" + token;
		Mockito.when(confirmationTokenRepository.findByConfirmationToken(token)).thenReturn(confirmationToken);
		Mockito.when(doctorService.findDoctorByEmail("elslouisette@gmail.com")).thenReturn(doctor);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(uri);
		mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().is2xxSuccessful());
		
	}

}
