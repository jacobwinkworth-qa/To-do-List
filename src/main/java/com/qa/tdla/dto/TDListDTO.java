package com.qa.tdla.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Profile({"dev", "prod"})
public class TdListDTO {
	
	private Long id;
	private String topic;
	
	private List<TaskDTO> tasks = new ArrayList<>();;

}
