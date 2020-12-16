package com.qa.tdla.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.tdla.dto.TaskDTO;
import com.qa.tdla.persistence.domain.Task;
import com.qa.tdla.persistence.repo.TaskRepo;

@SpringBootTest
@ActiveProfiles("dev")
class TaskServiceUnitTest {
	
	@Autowired
	private TaskService service;

	@MockBean
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
	
	// create
	@Test
	void createTest() {
		Task newTaskNoId = new Task("workout");
		Task newTask = new Task(5L, "workout");
		when(this.repo.save(newTaskNoId)).thenReturn(newTask);
		assertThat(this.service.create(newTaskNoId))
				.isEqualTo(this.mapToDTO(newTask));
		verify(this.repo, atLeastOnce()).save(newTaskNoId);
	}
	
	// read all
	@Test
	void readAllTest() {
		when(this.repo.findAll()).thenReturn(LIST_OF_TASKS);
		
		List<TaskDTO> listOfTaskDTOs = LIST_OF_TASKS.stream().map(this::mapToDTO)
				.collect(Collectors.toList());
		
		assertThat(this.service.readAll()).isEqualTo(listOfTaskDTOs);
		verify(this.repo, atLeastOnce()).findAll();
	}
	
	// read one
	@Test
	void readOneTest() {
		Long id = 1L;
		when(this.repo.findById(id)).thenReturn(Optional.of(TEST_TASK_1));
		assertThat(this.service.readOne(id))
				.isEqualTo(this.mapToDTO(TEST_TASK_1));
		verify(this.repo, atLeastOnce()).findById(id);
	}
	
	// (partial) update
	@Test
	void partialUpdateNameTest() {
		Long id = 1L;
		
		Task updated = new Task(id, "water plants");
		TaskDTO updatedDTO = this.mapToDTO(updated);
		
		when(this.repo.findById(id)).thenReturn(Optional.of(TEST_TASK_1));
		when(this.repo.save(updated)).thenReturn(updated);
		
		assertThat(this.service.partialUpdateName(updatedDTO, id))
				.isEqualTo(updatedDTO);
		
		verify(this.repo, atLeastOnce()).findById(id);
		verify(this.repo, atLeastOnce()).save(updated);
	}
	
	// delete success
	@Test
	void deleteSuccessTest() {
		Long id = 1L;
		when(this.repo.existsById(id)).thenReturn(false);
		assertThat(this.service.delete(id)).isEqualTo(true);
		verify(this.repo, atLeastOnce()).existsById(id);
	}
	
	// delete failure
	@Test
	void deleteFailureTest() {
		Long id = 1L;
		when(this.repo.existsById(id)).thenReturn(true);
		assertThat(this.service.delete(id)).isEqualTo(false);
		verify(this.repo, atLeastOnce()).existsById(id);
	}

}
