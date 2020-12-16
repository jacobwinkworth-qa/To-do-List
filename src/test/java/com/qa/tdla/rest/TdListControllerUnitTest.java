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

import com.qa.tdla.dto.TdListDTO;
import com.qa.tdla.dto.TdListDTO;
import com.qa.tdla.persistence.domain.TdList;
import com.qa.tdla.service.TdListService;

@SpringBootTest
@ActiveProfiles("dev")
class TdListControllerUnitTest {
	
	@Autowired
	private TdListController controller;

	@MockBean
	private TdListService service;

	@Autowired
	private ModelMapper mapper;
	
	private final TdList TEST_TD_LIST_1 = new TdList("monday's list");
	private final TdList TEST_TD_LIST_2 = new TdList("tuesday's list");
	private final TdList TEST_TD_LIST_3 = new TdList("wednesday's list");
	private final TdList TEST_TD_LIST_4 = new TdList("thursday's list");
	
	private final List<TdList> LIST_OF_LISTS = List.of(TEST_TD_LIST_1, TEST_TD_LIST_2, TEST_TD_LIST_3, TEST_TD_LIST_4);
	
	private TdListDTO mapToDto(TdList tdList) {
		return this.mapper.map(tdList, TdListDTO.class);
	}
	
	// create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_TD_LIST_1)).thenReturn(this.mapToDto(TEST_TD_LIST_1));
		assertThat(new ResponseEntity<TdListDTO>(this.mapToDto(TEST_TD_LIST_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_TD_LIST_1));
		verify(this.service, atLeastOnce()).create(TEST_TD_LIST_1);

	}
	
	
	// read all
	@Test
	void readAllTest() throws Exception {
		List<TdListDTO> dtos = LIST_OF_LISTS.stream().map(this::mapToDto).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).readAll();

	}

	// read one
	@Test
	void readOneTest() throws Exception {
		when(this.service.readOne(TEST_TD_LIST_1.getId())).thenReturn(this.mapToDto(TEST_TD_LIST_1));
		assertThat(new ResponseEntity<TdListDTO>(this.mapToDto(TEST_TD_LIST_1), HttpStatus.OK))
				.isEqualTo(this.controller.readOne(TEST_TD_LIST_1.getId()));
		verify(this.service, atLeastOnce()).readOne(TEST_TD_LIST_1.getId());
	}

	// update
	@Test
	void partialUpdateTest() throws Exception {
		when(this.service.partialUpdateTopic(this.mapToDto(TEST_TD_LIST_1), TEST_TD_LIST_1.getId())).thenReturn(this.mapToDto(TEST_TD_LIST_1));
		assertThat(new ResponseEntity<TdListDTO>(this.mapToDto(TEST_TD_LIST_1), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.partialUpdateTopic(TEST_TD_LIST_1.getId(), this.mapToDto(TEST_TD_LIST_1)));
		verify(this.service, atLeastOnce()).partialUpdateTopic(this.mapToDto(TEST_TD_LIST_1), TEST_TD_LIST_1.getId());
	}

	// delete
	@Test
	void deleteSuccessTest() throws Exception {
		when(this.service.delete(TEST_TD_LIST_1.getId())).thenReturn(true);
		assertThat(this.controller.delete(TEST_TD_LIST_1.getId()))
				.isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		verify(this.service, atLeastOnce()).delete(TEST_TD_LIST_1.getId());

	}
	
	// delete
	@Test
	void deleteFailureTest() throws Exception {
		when(this.service.delete(TEST_TD_LIST_1.getId())).thenReturn(false);
		assertThat(this.controller.delete(TEST_TD_LIST_1.getId()))
				.isEqualTo(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
		verify(this.service, atLeastOnce()).delete(TEST_TD_LIST_1.getId());

		}


}
