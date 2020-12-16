package com.qa.tdla.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.tdla.dto.TaskDTO;
import com.qa.tdla.dto.TdListDTO;
import com.qa.tdla.persistence.domain.Task;
import com.qa.tdla.service.TaskService;

@RestController
@CrossOrigin
@RequestMapping("/task") // this is to further define the path
@Profile({"dev", "prod"})
public class TaskController {

	private TaskService service;

	@Autowired
	public TaskController(TaskService service) {
		super();
		this.service = service;
	}

	// create
	@PostMapping("/create")
	public ResponseEntity<TaskDTO> create(@RequestBody Task task) {
		TaskDTO created = this.service.create(task);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	// read all
	@GetMapping("/read")
	public ResponseEntity<List<TaskDTO>> read() {
		return ResponseEntity.ok(this.service.readAll());
	}

	// read one
	@GetMapping("/read/{id}")
	public ResponseEntity<TaskDTO> readOne(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.readOne(id));
	}

//	// update
//	@PutMapping("/update/{id}")
//	public ResponseEntity<TaskDTO> update(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
//		return new ResponseEntity<>(this.service.update(taskDTO, id), HttpStatus.ACCEPTED);
//	}
	
	// patch
	@PatchMapping("/patch/{id}")
	public ResponseEntity<TaskDTO> partialUpdateName(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
		return new ResponseEntity<>(this.service.partialUpdateName(taskDTO, id), HttpStatus.ACCEPTED);
	}

	// delete one
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<TaskDTO> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
