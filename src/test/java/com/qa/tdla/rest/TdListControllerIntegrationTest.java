package com.qa.tdla.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.tdla.dto.TdListDTO;
import com.qa.tdla.persistence.domain.Task;
import com.qa.tdla.persistence.domain.TdList;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:schema.sql",
		"classpath:td_list-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "dev")
class TdListControllerIntegrationTest {
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;
	
	private final TdList TEST_TD_LIST_1 = new TdList(1L, "monday's list");
	private final TdList TEST_TD_LIST_2 = new TdList(2L, "tuesday's list");
	private final TdList TEST_TD_LIST_3 = new TdList(3L, "wednesday's list");
	private final TdList TEST_TD_LIST_4 = new TdList(4L, "thursday's list");
	
	private final String URI = "/list";
	
	private final List<TdList> LIST_OF_TD_LISTS = List.of(TEST_TD_LIST_1, TEST_TD_LIST_2, TEST_TD_LIST_3, TEST_TD_LIST_4);
	
	private TdListDTO mapToDto(TdList tdList) {
		return this.mapper.map(tdList, TdListDTO.class);
	}
	
	// create
	@Test
	void createTest() throws Exception {
		TdListDTO testDTO = mapToDto(new TdList("friday's list"));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isCreated();

		TdListDTO testSavedDTO = mapToDto(new TdList("friday's list"));
		testSavedDTO.setId(5L);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);

		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);

	}
	
	// read all
	@Test
	void readAllTest() throws Exception {
		
		TEST_TD_LIST_1.setTasks(List.of(new Task(1L, "water dog")));
		TEST_TD_LIST_2.setTasks(List.of(new Task(2L, "walk plants")));
		TEST_TD_LIST_3.setTasks(List.of(new Task(3L, "5k run")));
		TEST_TD_LIST_4.setTasks(List.of(new Task(4L, "meal prep")));
		
		RequestBuilder request = get(URI + "/read").contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isOk();
		
		List<TdListDTO> testReadDTO = LIST_OF_TD_LISTS.stream().map(this::mapToDto).collect(Collectors.toList());
		String testReadDTOAsJSON = this.jsonifier.writeValueAsString(testReadDTO);
		
		ResultMatcher checkBody = content().json(testReadDTOAsJSON);
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}
	
	// read one
	@Test
	void readOneTest() throws Exception {
		
		Long id = 1L;
		
		TEST_TD_LIST_1.setTasks(List.of(new Task(1L, "water dog")));
		
		RequestBuilder request = get(URI + "/read/" + id).contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isOk();
		
		TdListDTO testReadOneDTO = mapToDto(TEST_TD_LIST_1);
		String testReadOneDTOAsJSON = this.jsonifier.writeValueAsString(testReadOneDTO);
		
		ResultMatcher checkBody = content().json(testReadOneDTOAsJSON);
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
		
	}
	
	// (partial) update
	@Test
	void partialUpdateTopicTest() throws Exception {
		
		Long id = 1L;
		
		TdList updated = new TdList("today's list");
		updated.setTasks(List.of(new Task(1L, "water dog")));
		
		TdListDTO testDTO = mapToDto(updated);
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = patch(URI + "/patch/" + id).contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isAccepted();
		
		TdList saved = new TdList("today's list");
		saved.setTasks(List.of(new Task(1L, "water dog")));
		
		TdListDTO testSavedDTO = mapToDto(saved);
		testSavedDTO.setId(id);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);
		
		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
		
	}
	
	// delete
	@Test
	void deleteTest() throws Exception {
		
		Long id = 1L;
		
		RequestBuilder request = delete(URI + "/delete/" + id).contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isNoContent();
		
		this.mvc.perform(request).andExpect(checkStatus);
		
	}

}
