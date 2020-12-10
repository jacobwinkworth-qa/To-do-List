package com.qa.tdla.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // classes that represent tables in our DB
@Data
@NoArgsConstructor
@Profile({"dev", "prod"})
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;
	
	@ManyToOne
	private TdList tdList;

	public Task(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Task(String name) {
		super();
		this.name = name;
	}

}
