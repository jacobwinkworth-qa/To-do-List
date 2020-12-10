package com.qa.tdla.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.qa.tdla.dto.TDListDTO;
import com.qa.tdla.persistence.domain.TDList;
import com.qa.tdla.persistence.repo.TDListRepo;
import com.qa.tdla.persistence.repo.TaskRepo;
import com.qa.tdla.util.SpringBeanUtil;

@Service
@Profile({"dev", "prod"})
public class TDListService {
	
	private TDListRepo repo;
	private ModelMapper mapper;
	
	@Autowired
	public TDListService(TDListRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private TDListDTO mapToDTO(TDList tdList) {
		return this.mapper.map(tdList, TDListDTO.class);		
	}
	
	// create
	public TDListDTO create(TDList task) {
		return this.mapToDTO(this.repo.save(task));
	}
	
	// read all
	public List<TDListDTO> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// read one method
	public TDListDTO readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow());
	}
	
	// update
	public TDListDTO update(TDListDTO tdListDTO, Long id) {
		TDList toUpdate = this.repo.findById(id).orElseThrow();
		toUpdate.setTopic(tdListDTO.getTopic());
		SpringBeanUtil.mergeNotNull(tdListDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));

	}
	
	// delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
	
}
