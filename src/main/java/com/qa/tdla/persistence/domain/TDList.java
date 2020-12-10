package com.qa.tdla.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // classes that represent tables in our DB
@Data
@NoArgsConstructor
public class TDList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String topic;

	public TDList(Long id, String topic) {
		super();
		this.id = id;
		this.topic = topic;
	}
	
	public TDList(String topic) {
		super();
		this.topic = topic;
	}

}
