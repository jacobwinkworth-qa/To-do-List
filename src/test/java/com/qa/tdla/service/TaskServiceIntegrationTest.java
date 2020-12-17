package com.qa.tdla.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.qa.tdla.dto.TaskDTO;
import com.qa.tdla.persistence.domain.Task;
import com.qa.tdla.persistence.repo.TaskRepo;

@SpringBootTest
@ActiveProfiles(profiles = "dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TaskServiceIntegrationTest {
	
	@Autowired
	private TaskService service;

	@Autowired
	private TaskRepo repo;

	@Autowired
	private ModelMapper mapper;
	
	// test data
	private final Task TEST_TASK_1 = new Task(1L, "water dog");
	private final Task TEST_TASK_2 = new Task(2L, "walk plants");
	private final Task TEST_TASK_3 = new Task(3L, "5k run");
	private final Task TEST_TASK_4 = new Task(4L, "meal prep");
	
	private final List<Task> LIST_OF_TASKS = List.of(TEST_TASK_1, TEST_TASK_2, TEST_TASK_3, TEST_TASK_4);

	private TaskDTO mapToDTO(Task task) {
		return this.mapper.map(task, TaskDTO.class);
	}
	
	private Task mapToPOJO(TaskDTO taskDTO) {
		return this.mapper.map(taskDTO, Task.class);
	}
	
	@BeforeEach
	void setup() {
		this.repo.saveAll(LIST_OF_TASKS);
	}

	// create
	@Test
	void createTest() throws Exception {
		assertThat(this.service.create(TEST_TASK_1)).isEqualTo(this.mapToDTO(TEST_TASK_1));
	}
	
	// read all
	@Test
	void readAllTest() throws Exception {
		assertThat(this.service.readAll().stream().map(this::mapToPOJO).collect(Collectors.toList()))
				.isEqualTo(LIST_OF_TASKS);
	}
	
	// read one
	@Test
	void readOneTest() throws Exception {
		Long id = 1L;
		assertThat(this.service.readOne(id)).isEqualTo(this.mapToDTO(TEST_TASK_1));
	}
	
	// (partial) update
	@Test
	void partialUpdateNameTest() throws Exception {
		Long id = 1L;
		Task updated = new Task(id, "water plants");
		TaskDTO updatedDTO = this.mapToDTO(updated);
		assertThat(this.service.partialUpdateName(updatedDTO, id))
				.isEqualTo(updatedDTO);
	}
	
	// delete
	@Test
	void deleteTest() throws Exception {
		Long id = 1L;
		assertThat(this.service.delete(id)).isEqualTo(true);
	}

}
