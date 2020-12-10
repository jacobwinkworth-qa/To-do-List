package com.qa.tdla.dto;

import java.util.List;

import org.springframework.context.annotation.Profile;

import com.qa.tdla.persistence.domain.Task;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Profile({"dev", "prod"})
public class TdListDTO {
	
	private Long id;
	private String topic;
	
	private List<Task> tasks;

}
