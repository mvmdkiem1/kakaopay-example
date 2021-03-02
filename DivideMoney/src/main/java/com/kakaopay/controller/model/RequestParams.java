package com.kakaopay.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @AllArgsConstructor @NoArgsConstructor

public class RequestParams {

	private int money;
	
	private int count;
	
	private String tokken;
}
