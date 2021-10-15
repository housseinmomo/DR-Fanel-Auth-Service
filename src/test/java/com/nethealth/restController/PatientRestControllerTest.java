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
import com.nethealth.entities.Location;
import com.nethealth.entities.Patient;
import com.nethealth.entities.Person;
import com.nethealth.repository.ConfirmationTokenRepository;
import com.nethealth.repository.DoctorRepository;
import com.nethealth.service.PatientService;

@RunWith(SpringRunner.class)
@WebMvcTest(PatientRestController.class)
class PatientRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PatientService patientService;
	@MockBean
	JavaMailSender javaMailSender;
	@MockBean
	ConfirmationTokenRepository confirmationTokenRepository;
	@MockBean
	private DoctorRepository doctorRepositry;
	@Autowired
	private ObjectMapper objectMapper;
	
	static Patient patient;
	static ConfirmationToken confirmationToken;
	
	
	@BeforeAll
    public static void setUp(){
		List<String> roles = new ArrayList<>();
		patient = new Patient(null, new Person(null, "bbili", "bbili@gmail.com", "TTili123", "mimi", "mimi", new Date(), "female",
				"+221 47 78 95", new Location(null, "Senegal", "dakar", "point e", "98000"), roles, 0));
		confirmationToken = new ConfirmationToken("bbili@gmail.com");
    }

	@Test
	void testSave() throws Exception {
		Mockito.when(patientService.savePatient(patient)).thenReturn(patient);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient));
		mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().is2xxSuccessful());
	}

	@Test
	void testActivateAccount()throws Exception {
		String token = confirmationToken.getConfirmationToken();
		String uri = "/api/patients/account/confirm?link=" + token;
		Mockito.when(confirmationTokenRepository.findByConfirmationToken(token)).thenReturn(confirmationToken);
		Mockito.when(patientService.findPatientByEmail("bbili@gmail.com")).thenReturn(patient);
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(uri);
		mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().is2xxSuccessful());
		
	}

}
