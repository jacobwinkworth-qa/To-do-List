package com.qa.tdla.dto;


import org.springframework.context.annotation.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Profile({"dev", "prod"})
public class TaskDTO {
	
	private Long id;
	private String name;

}
