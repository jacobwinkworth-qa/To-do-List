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

import com.qa.tdla.dto.TdListDTO;
import com.qa.tdla.persistence.domain.TdList;
import com.qa.tdla.service.TdListService;


@RestController
@CrossOrigin
@RequestMapping("/list") // this is to further define the path
@Profile({"dev", "prod"})
public class TdListController {

	private TdListService service;

	@Autowired
	public TdListController(TdListService service) {
		super();
		this.service = service;
	}

	// create
	@PostMapping("/create")
	public ResponseEntity<TdListDTO> create(@RequestBody TdList tdList) {
		TdListDTO created = this.service.create(tdList);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	// read all
	@GetMapping("/read")
	public ResponseEntity<List<TdListDTO>> read() {
		return ResponseEntity.ok(this.service.readAll());
	}

	// read one
	@GetMapping("/read/{id}")
	public ResponseEntity<TdListDTO> readOne(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.readOne(id));
	}

//	// update
//	@PutMapping("/update/{id}")
//	public ResponseEntity<TdListDTO> update(@PathVariable Long id, @RequestBody TdListDTO tdListDTO) {
//		return new ResponseEntity<>(this.service.update(tdListDTO, id), HttpStatus.ACCEPTED);
//	}
	
	// patch
	@PatchMapping("/patch/{id}")
	public ResponseEntity<TdListDTO> partialUpdateTopic(@PathVariable Long id, @RequestBody TdListDTO tdListDTO) {
		return new ResponseEntity<>(this.service.partialUpdateTopic(tdListDTO, id), HttpStatus.ACCEPTED);
	}

	// delete one
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<TdListDTO> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
