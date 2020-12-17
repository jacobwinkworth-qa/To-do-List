package com.qa.tdla.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
	
	@OneToMany(mappedBy = "tdList", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Task> tasks;

	public TdList(Long id, String topic) {
		super();
		this.id = id;
		this.topic = topic;
		this.tasks = new ArrayList<>();
	}
	
	public TdList(String topic) {
		super();
		this.topic = topic;
		this.tasks = new ArrayList<>();
	}

}
