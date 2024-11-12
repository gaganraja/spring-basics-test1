package com.example.authenticatingldap.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.authenticatingldap.model.PersonData;
import com.example.authenticatingldap.model.QualificationData;
import com.example.authenticatingldap.security.WebSecurityConfig;
import com.example.authenticatingldap.service.DataService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(WebSecurityConfig.class)
public class DataControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@InjectMocks
	private DataController dataController;
	
	@MockBean
	private DataService dataService;
	
	@MockBean
	private PersonData mockPerson;
	
	@MockBean
	private QualificationData mockQualification;
	
	//@MockBean
	//private Long mockId;
	
	@Test
	public void contextLoads() {
		assertThat(dataController).isNotNull();
	}
	
	@Test
	@WithMockUser(username = "bob", roles = {"SUBMANAGER"})
	public void testGetPersonData_Forbidden_Role() throws Exception {
		mockMvc.perform(get("/person/1000"))
        .andExpect(status().isForbidden()); 
	}
	
	@Test
	@WithMockUser(username = "bob", roles = {"DEVELOPER"})
	public void testGetPersonData_Allowed_Role() throws Exception {
		mockMvc.perform(get("/person/1000"))
        .andExpect(status().isOk()); 
	}
	
	@Test
	@WithMockUser(username = "ben", roles = {"MANAGER"})
	public void testGetPersonData_PathVariable_NOT_NUMERIC() throws Exception {
		mockMvc.perform(get("/person/1AYU67"))
        .andExpect(status().isBadRequest()); 
	}
	
	@Test
	@WithMockUser(username = "ben", roles = {"MANAGER"})
	public void testGetPersonData_Exception_Thrown_While_Accessing_Resource() throws Exception {
		when(dataService.getPersonById(1000l)).thenThrow(new Exception());
		mockMvc.perform(get("/person/1000"))
        .andExpect(status().isInternalServerError()); 
	}
	
	@Test
	@WithMockUser(username = "ben", roles = {"MANAGER"})
	public void testGetPersonData_Successfully() throws Exception {
		when(mockPerson.getId()).thenReturn(1000l);
		when(mockPerson.getFirstName()).thenReturn("testName");
		when(dataService.getPersonById(1000l)).thenReturn(mockPerson);
		
		mockMvc.perform(get("/person/1000"))
        .andExpect(status().isOk()) 
        .andExpect(jsonPath("$.id").value(1000)) 
        .andExpect(jsonPath("$.firstName").value("testName")); 
	}
	
	@Test
	@WithMockUser(username = "bob", roles = {"DEVELOPER"})
	public void testRecordPersonQualification_Forbidden_Role() throws Exception {
		QualificationData qualification = new QualificationData();
		mockMvc.perform(put("/person-qualification/1000")
		.contentType("application/json")
		.content(objectMapper.writeValueAsString(qualification)))
        .andExpect(status().isForbidden()); 
	}
	
	@Test
	@WithMockUser(username = "ben", roles = {"MANAGER"})
	public void testRecordPersonQualification_Allowed_Role() throws Exception {
		when(dataService.getPersonById(1000l)).thenReturn(mockPerson);
		when(dataService.isPersonQualificationAlreadyExist(any(), any())).thenReturn(false);
		
		mockMvc.perform(put("/person-qualification/1000")
		.contentType("application/json")
		.content(objectMapper.writeValueAsString(mockQualification)))
        .andExpect(status().isOk()); 
	}
	
	@Test
	@WithMockUser(username = "ben", roles = {"MANAGER"})
	public void testRecordPersonQualification_Person_Does_Not_Exist() throws Exception {
		when(dataService.getPersonById(1000l)).thenReturn(null);
		
		mockMvc.perform(put("/person-qualification/1000")
		.contentType("application/json")
		.content(objectMapper.writeValueAsString(mockQualification)))
        .andExpect(status().isNotFound()); 
	}
	
	@Test
	@WithMockUser(username = "ben", roles = {"MANAGER"})
	public void testRecordPersonQualification_Already_Exist() throws Exception {
		when(dataService.getPersonById(1000l)).thenReturn(mockPerson);
		when(dataService.isPersonQualificationAlreadyExist(any(), any())).thenReturn(true);
		
		mockMvc.perform(put("/person-qualification/1000")
		.contentType("application/json")
		.content(objectMapper.writeValueAsString(mockQualification)))
        .andExpect(status().isConflict()); 
	}
	
	@Test
	@WithMockUser(username = "ben", roles = {"MANAGER"})
	public void testRecordPersonQualification_Exception_Thrown_While_Accessing_Resource() throws Exception {
		when(dataService.getPersonById(1000l)).thenReturn(mockPerson);
		when(dataService.isPersonQualificationAlreadyExist(any(), any())).thenThrow(new Exception());
		
		mockMvc.perform(put("/person-qualification/1000")
		.contentType("application/json")
		.content(objectMapper.writeValueAsString(mockQualification)))
        .andExpect(status().isInternalServerError()); 
	}
	
	@Test
	@WithMockUser(username = "ben", roles = {"MANAGER"})
	public void testRecordPersonQualification_Exception_Thrown_While_Creating_Resource() throws Exception {
		when(dataService.getPersonById(1000l)).thenReturn(mockPerson);
		when(dataService.isPersonQualificationAlreadyExist(any(), any())).thenReturn(false);
		when(dataService.createPersonQualification(any(), any())).thenThrow(new Exception());
		
		mockMvc.perform(put("/person-qualification/1000")
		.contentType("application/json")
		.content(objectMapper.writeValueAsString(mockQualification)))
        .andExpect(status().isInternalServerError()); 
	}
	
}
