package com.qa.tdla.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.qa.tdla.dto.TdListDTO;
import com.qa.tdla.persistence.domain.TdList;
import com.qa.tdla.persistence.repo.TdListRepo;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TdListServiceIntegrationTest {

	@Autowired
	private TdListService service;

	@Autowired
	private TdListRepo repo;

	@Autowired
	private ModelMapper mapper;
	
	// test data
	private final TdList TEST_TD_LIST_1 = new TdList(1L, "monday's list");
	private final TdList TEST_TD_LIST_2 = new TdList(2L, "tuesday's list");
	private final TdList TEST_TD_LIST_3 = new TdList(3L, "wednesday's list");
	private final TdList TEST_TD_LIST_4 = new TdList(4L, "thursday's list");
	
	private final List<TdList> LIST_OF_TD_LISTS = List.of(TEST_TD_LIST_1, TEST_TD_LIST_2, TEST_TD_LIST_3, TEST_TD_LIST_4);

	private TdListDTO mapToDTO(TdList tdList) {
		return this.mapper.map(tdList, TdListDTO.class);
	}
	
	private TdList mapToPOJO(TdListDTO taskDTO) {
		return this.mapper.map(taskDTO, TdList.class);
	}
	
	@BeforeEach
	void setup() {
		this.repo.saveAll(LIST_OF_TD_LISTS);
	}

	// create
	@Test
	void createTest() throws Exception {
		assertThat(this.service.create(TEST_TD_LIST_1)).isEqualTo(this.mapToDTO(TEST_TD_LIST_1));
	}
	
	// read all
	@Test
	void readAllTest() throws Exception {
		assertThat(this.service.readAll().stream().map(this::mapToPOJO).collect(Collectors.toList()))
				.isEqualTo(LIST_OF_TD_LISTS);
	}
	
	// read one
	@Test
	void readOneTest() throws Exception {
		Long id = 1L;
		assertThat(this.service.readOne(id)).isEqualTo(this.mapToDTO(TEST_TD_LIST_1));
	}
	
	// (partial) update
	@Test
	void partialUpdateNameTest() throws Exception {
		Long id = 1L;
		TdList updated = new TdList(id, "today's list");
		TdListDTO updatedDTO = this.mapToDTO(updated);
		assertThat(this.service.partialUpdateTopic(updatedDTO, id))
				.isEqualTo(updatedDTO);
	}
	
	// delete
	@Test
	void deleteTest() throws Exception {
		Long id = 1L;
		assertThat(this.service.delete(id)).isEqualTo(true);
	}

}
