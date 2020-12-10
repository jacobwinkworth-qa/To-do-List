package com.qa.tdla.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // classes that represent tables in our DB
@Data
@NoArgsConstructor
@Profile({"dev", "prod"})
public class TdList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String topic;

	public TdList(Long id, String topic) {
		super();
		this.id = id;
		this.topic = topic;
	}
	
	public TdList(String topic) {
		super();
		this.topic = topic;
	}

}
