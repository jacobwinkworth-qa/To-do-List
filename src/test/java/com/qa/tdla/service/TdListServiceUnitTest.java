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

import com.qa.tdla.dto.TdListDTO;
import com.qa.tdla.persistence.domain.TdList;
import com.qa.tdla.persistence.repo.TdListRepo;

@SpringBootTest
@ActiveProfiles("dev")
class TdListServiceUnitTest {
	
	@Autowired
	private TdListService service;

	@MockBean
	private TdListRepo repo;

	@Autowired
	private ModelMapper mapper;
	
	// test data
	private final TdList TEST_TD_LIST_1 = new TdList(1L, "water dog");
	private final TdList TEST_TD_LIST_2 = new TdList(2L, "walk plants");
	private final TdList TEST_TD_LIST_3 = new TdList(3L, "5k run");
	private final TdList TEST_TD_LIST_4 = new TdList(4L, "meal prep");
	
	private final List<TdList> LIST_OF_TD_LISTS = List.of(TEST_TD_LIST_1, TEST_TD_LIST_2, TEST_TD_LIST_3, TEST_TD_LIST_4);
	
	private TdListDTO mapToDTO(TdList task) {
		return this.mapper.map(task, TdListDTO.class);		
	}
	
	// create
	@Test
	void createTest() {
		TdList newTaskNoId = new TdList("workout");
		TdList newTask = new TdList(5L, "workout");
		when(this.repo.save(newTaskNoId)).thenReturn(newTask);
		assertThat(this.service.create(newTaskNoId))
				.isEqualTo(this.mapToDTO(newTask));
		verify(this.repo, atLeastOnce()).save(newTaskNoId);
	}
	
	// read all
	@Test
	void readAllTest() {
		when(this.repo.findAll()).thenReturn(LIST_OF_TD_LISTS);
		
		List<TdListDTO> listOfTaskDTOs = LIST_OF_TD_LISTS.stream().map(this::mapToDTO)
				.collect(Collectors.toList());
		
		assertThat(this.service.readAll()).isEqualTo(listOfTaskDTOs);
		verify(this.repo, atLeastOnce()).findAll();
	}
	
	// read one
	@Test
	void readOneTest() {
		Long id = 1L;
		when(this.repo.findById(id)).thenReturn(Optional.of(TEST_TD_LIST_1));
		assertThat(this.service.readOne(id))
				.isEqualTo(this.mapToDTO(TEST_TD_LIST_1));
		verify(this.repo, atLeastOnce()).findById(id);
	}
	
	// (partial) update
	@Test
	void partialUpdateNameTest() {
		Long id = 1L;
		
		TdList updated = new TdList(id, "water plants");
		TdListDTO updatedDTO = this.mapToDTO(updated);
		
		when(this.repo.findById(id)).thenReturn(Optional.of(TEST_TD_LIST_1));
		when(this.repo.save(updated)).thenReturn(updated);
		
		assertThat(this.service.partialUpdateTopic(updatedDTO, id))
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
