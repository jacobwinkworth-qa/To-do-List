package com.qa.tdla.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.qa.tdla.dto.TdListDTO;
import com.qa.tdla.persistence.domain.TdList;
import com.qa.tdla.persistence.repo.TdListRepo;
import com.qa.tdla.util.SpringBeanUtil;

@Service
@Profile({"dev", "prod"})
public class TdListService {
	
	private TdListRepo repo;
	private ModelMapper mapper;
	
	@Autowired
	public TdListService(TdListRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private TdListDTO mapToDTO(TdList tdList) {
		return this.mapper.map(tdList, TdListDTO.class);		
	}
	
	// create
	public TdListDTO create(TdList task) {
		return this.mapToDTO(this.repo.save(task));
	}
	
	// read all
	public List<TdListDTO> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// read one
	public TdListDTO readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow());
	}
	
	// update
	public TdListDTO update(TdListDTO tdListDTO, Long id) {
		TdList toUpdate = this.repo.findById(id).orElseThrow();
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
