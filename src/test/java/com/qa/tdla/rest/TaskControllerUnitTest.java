package com.qa.tdla.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.qa.tdla.dto.TaskDTO;
import com.qa.tdla.persistence.domain.Task;
import com.qa.tdla.rest.TaskController;
import com.qa.tdla.service.TaskService;

@SpringBootTest
@ActiveProfiles("dev")
class TaskControllerUnitTest {
	
	@Autowired
	private TaskController controller;

	@MockBean
	private TaskService service;

	@Autowired
	private ModelMapper mapper;
	
	// test data
	private final Task TEST_TASK_1 = new Task(1L, "water dog");
	private final Task TEST_TASK_2 = new Task(2L, "walk plants");
	private final Task TEST_TASK_3 = new Task(3L, "5k run");
	private final Task TEST_TASK_4 = new Task(4L, "meal prep");
	
	private final List<Task> LIST_OF_TASKS = List.of(TEST_TASK_1, TEST_TASK_2, TEST_TASK_3, TEST_TASK_4);

	private TaskDTO mapToDto(Task task) {
		return this.mapper.map(task, TaskDTO.class);
	}
	
	// create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_TASK_1)).thenReturn(this.mapToDto(TEST_TASK_1));
		assertThat(new ResponseEntity<TaskDTO>(this.mapToDto(TEST_TASK_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_TASK_1));
		verify(this.service, atLeastOnce()).create(TEST_TASK_1);

	}
	
	// read all
	@Test
	void readAllTest() throws Exception {
		List<TaskDTO> dtos = LIST_OF_TASKS.stream().map(this::mapToDto).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).readAll();

	}

	// read one
	@Test
	void readOneTest() throws Exception {
		when(this.service.readOne(TEST_TASK_1.getId())).thenReturn(this.mapToDto(TEST_TASK_1));
		assertThat(new ResponseEntity<TaskDTO>(this.mapToDto(TEST_TASK_1), HttpStatus.OK))
				.isEqualTo(this.controller.readOne(TEST_TASK_1.getId()));
		verify(this.service, atLeastOnce()).readOne(TEST_TASK_1.getId());
	}

	// update
	@Test
	void partialUpdateTest() throws Exception {
		when(this.service.partialUpdateName(this.mapToDto(TEST_TASK_1), TEST_TASK_1.getId())).thenReturn(this.mapToDto(TEST_TASK_1));
		assertThat(new ResponseEntity<TaskDTO>(this.mapToDto(TEST_TASK_1), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.partialUpdateName(TEST_TASK_1.getId(), this.mapToDto(TEST_TASK_1)));
		verify(this.service, atLeastOnce()).partialUpdateName(this.mapToDto(TEST_TASK_1), TEST_TASK_1.getId());
	}

	// delete
	@Test
	void deleteSuccessTest() throws Exception {
		when(this.service.delete(TEST_TASK_1.getId())).thenReturn(true);
		assertThat(this.controller.delete(TEST_TASK_1.getId()))
				.isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		verify(this.service, atLeastOnce()).delete(TEST_TASK_1.getId());

	}
	
	// delete
		@Test
		void deleteFailureTest() throws Exception {
			when(this.service.delete(TEST_TASK_1.getId())).thenReturn(false);
			assertThat(this.controller.delete(TEST_TASK_1.getId()))
					.isEqualTo(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
			verify(this.service, atLeastOnce()).delete(TEST_TASK_1.getId());

		}

}
