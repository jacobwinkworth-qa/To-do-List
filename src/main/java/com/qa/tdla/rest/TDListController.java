package com.qa.tdla.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.tdla.dto.TDListDTO;
import com.qa.tdla.persistence.domain.TDList;
import com.qa.tdla.service.TDListService;

@RestController
@CrossOrigin
@RequestMapping("/list") // this is to further define the path
@Profile({"dev", "prod"})
public class TDListController {

	private TDListService service;

	@Autowired
	public TDListController(TDListService service) {
		super();
		this.service = service;
	}

	// Create
	@PostMapping("/create")
	public ResponseEntity<TDListDTO> create(@RequestBody TDList tdList) {
		TDListDTO created = this.service.create(tdList);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	// read all
	@GetMapping("/read")
	public ResponseEntity<List<TDListDTO>> read() {
		return ResponseEntity.ok(this.service.readAll());
	}

	// read one
	@GetMapping("/read/{id}")
	public ResponseEntity<TDListDTO> readOne(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.readOne(id));
	}

	// update
	@PutMapping("/update/{id}")
	public ResponseEntity<TDListDTO> update(@PathVariable Long id, @RequestBody TDListDTO tdListDTO) {
		return new ResponseEntity<>(this.service.update(tdListDTO, id), HttpStatus.ACCEPTED);
	}

	// Delete one
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<TDListDTO> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
	}

}
