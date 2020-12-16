package com.qa.tdla.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:schema.sql",
		"classpath:td_list-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "dev")
class TdListServiceIntegrationTest {

	// create
	@Test
	void createTest() {
		
	}
	
	// read all
	@Test
	void readAllTest() {
		
	}
	
	// read one
	@Test
	void readOneTest() {
		
	}
	
	// (partial) update
	@Test
	void partialUpdateNameTest() {
		
	}
	
	// delete success
	@Test
	void deleteSuccessTest() {
		
	}
	
	// delete failure
	@Test
	void deleteFailureTest() {
		
	}

}
