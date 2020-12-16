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
import com.qa.tdla.dto.TaskDTO;
import com.qa.tdla.persistence.domain.Task;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:task-schema.sql",
		"classpath:task-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "dev")
public class TaskControllerIntegrationTest {
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;
	
	// test data
	private final Task TEST_TASK_1 = new Task(1L, "water dog");
	private final Task TEST_TASK_2 = new Task(2L, "walk plants");
	private final Task TEST_TASK_3 = new Task(3L, "5k run");
	private final Task TEST_TASK_4 = new Task(4L, "meal prep");
	
	private final String URI = "/task";
	
	private final List<Task> LIST_OF_TASKS = List.of(TEST_TASK_1, TEST_TASK_2, TEST_TASK_3, TEST_TASK_4);

	private TaskDTO mapToDto(Task task) {
		return this.mapper.map(task, TaskDTO.class);
	}
	
	// create
	@Test
	void createTest() throws Exception {
		TaskDTO testDTO = mapToDto(new Task("play football"));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isCreated();

		TaskDTO testSavedDTO = mapToDto(new Task("play football"));
		testSavedDTO.setId(5L);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);

		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);

	}
	
	// read all
	@Test
	void readAllTest() throws Exception {
		
		RequestBuilder request = get(URI + "/read").contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isOk();
		
		List<TaskDTO> testReadDTO = LIST_OF_TASKS.stream().map(this::mapToDto).collect(Collectors.toList());
		String testReadDTOAsJSON = this.jsonifier.writeValueAsString(testReadDTO);
		
		ResultMatcher checkBody = content().json(testReadDTOAsJSON);
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}
	
	// read one
	@Test
	void readOneTest() throws Exception {
		
		Long id = 1L;
		
		RequestBuilder request = get(URI + "/read/" + id).contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isOk();
		
		TaskDTO testReadOneDTO = mapToDto(TEST_TASK_1);
		String testReadOneDTOAsJSON = this.jsonifier.writeValueAsString(testReadOneDTO);
		
		ResultMatcher checkBody = content().json(testReadOneDTOAsJSON);
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
		
	}
	
	// (partial) update
	@Test
	void partialUpdateNameTest() throws Exception {
		
		Long id = 1L;
		
		TaskDTO testDTO = mapToDto(new Task("water plants"));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = patch(URI + "/patch/" + id).contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isAccepted();
		
		TaskDTO testSavedDTO = mapToDto(new Task("water plants"));
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
