package com.qa.tdla.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.qa.tdla.dto.TaskDTO;
import com.qa.tdla.dto.TdListDTO;
import com.qa.tdla.persistence.domain.Task;
import com.qa.tdla.persistence.domain.TdList;
import com.qa.tdla.persistence.repo.TaskRepo;
import com.qa.tdla.util.SpringBeanUtil;

@Service
@Profile({"dev", "prod"})
public class TaskService {
	
	private TaskRepo repo;
	private ModelMapper mapper;
	
	@Autowired
	public TaskService(TaskRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private TaskDTO mapToDTO(Task task) {
		return this.mapper.map(task, TaskDTO.class);		
	}
	
	// create
	public TaskDTO create(Task task) {
		return this.mapToDTO(this.repo.save(task));
	}
	
	// read all
	public List<TaskDTO> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// read one
	public TaskDTO readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow());
	}
	
//	// update
//	public TaskDTO update(TaskDTO taskDto, Long id) {
//		Task toUpdate = this.repo.findById(id).orElseThrow();
//		toUpdate.setName(taskDto.getName());
//		SpringBeanUtil.mergeNotNull(taskDto, toUpdate);
//		return this.mapToDTO(this.repo.save(toUpdate));
//
//	}
	
	// patch
	public TaskDTO partialUpdateName(TaskDTO taskDto, Long id) {
		Task toUpdate = this.repo.findById(id).orElseThrow();
		toUpdate.setName(taskDto.getName());
		SpringBeanUtil.mergeNotNull(taskDto, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));

	}
	
	// delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
}
