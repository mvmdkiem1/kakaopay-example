package com.kakaopay.vo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @AllArgsConstructor @NoArgsConstructor 
public class DivideMoney {
	private int orderNo;
	
	private String groupId;
	
	private String roomId;
	
	private String userId;

	private int count;
	
	private BigDecimal money;
	
	private String tokken;
	
	private Date regDate;
	
	private String method;
}
