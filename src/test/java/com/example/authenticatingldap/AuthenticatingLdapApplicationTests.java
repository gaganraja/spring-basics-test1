/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.authenticatingldap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;

import com.example.authenticatingldap.controller.DataController;
import com.example.authenticatingldap.model.PersonData;
import com.example.authenticatingldap.model.QualificationData;
import com.example.authenticatingldap.security.WebSecurityConfig;
import com.example.authenticatingldap.service.DataService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Rob Winch
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(WebSecurityConfig.class)
public class AuthenticatingLdapApplicationTests {
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
	
	@Test
	public void contextLoads() {
		assertThat(dataController).isNotNull();
	}

	@Test
	public void loginWithValidUserThenAuthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin()
			.user("ben")
			.password("benspassword");

		mockMvc.perform(login)
			.andExpect(authenticated().withUsername("ben"));
	}

	@Test
	public void loginWithInvalidUserThenUnauthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin()
			.user("invalid")
			.password("invalidpassword");

		mockMvc.perform(login)
			.andExpect(unauthenticated());
	}
	
	@Test
	@WithMockUser(username = "bob", roles = {"SUBMANAGERS"})
	public void testGetPersonData_Forbidden_Role() throws Exception {
		mockMvc.perform(get("/person/1000"))
        .andExpect(status().isForbidden()); 
	}
	
	@Test
	@WithMockUser(username = "ben", roles = {"MANAGERS"})
	public void testGetPersonData_Invalid_PathVariable() throws Exception {
		mockMvc.perform(get("/person/1AYU67"))
        .andExpect(status().isBadRequest()); 
	}
	
	@Test
	@WithMockUser(username = "ben", roles = {"MANAGERS"})
	public void testGetPersonData_Exception_Thrown() throws Exception {
		when(dataService.getPersonById(1000l)).thenThrow(new Exception());
		mockMvc.perform(get("/person/1000"))
        .andExpect(status().isNotFound()); 
	}
	
	@Test
	@WithMockUser(username = "ben", roles = {"MANAGERS"})
	public void testGetPersonData() throws Exception {
		when(mockPerson.getId()).thenReturn(1000l);
		when(mockPerson.getFirstName()).thenReturn("testName");
		when(dataService.getPersonById(1000l)).thenReturn(mockPerson);
		
		mockMvc.perform(get("/person/1000"))
        .andExpect(status().isOk()) 
        .andExpect(jsonPath("$.id").value(1000)) 
        .andExpect(jsonPath("$.firstName").value("testName")); 
	}
	
	@Test
	@WithMockUser(username = "bob", roles = {"DEVELOPERS"})
	public void testRecordPersonQualification_Forbidden_Role() throws Exception {
		QualificationData qualification = new QualificationData();
		mockMvc.perform(put("/person-qualification/1000")
		.contentType("application/json")
		.content(objectMapper.writeValueAsString(qualification)))
        .andExpect(status().isForbidden()); 
	}
	
	@Test
	@WithMockUser(username = "ben", roles = {"MANAGERS"})
	public void testRecordPersonQualification_Role_Allowed() throws Exception {
		QualificationData qualification = new QualificationData();
		when(dataService.getPersonById(1000l)).thenReturn(mockPerson);
		when(dataService.isPersonQualificationAlreadyExist(1000l, qualification)).thenReturn(false);
		
		mockMvc.perform(put("/person-qualification/1000")
		.contentType("application/json")
		.content(objectMapper.writeValueAsString(qualification)))
        .andExpect(status().isOk()); 
	}
}
