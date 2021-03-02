package com.kakaopay.repository.mysql.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @AllArgsConstructor @NoArgsConstructor 
@Data
@Entity
@Table(name="dividemoney")
public class DivideMoneyEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int _id;
	private int orderNo;
	private String roomId;
	private String userId;
	private String userName;
	private int count;
	private BigDecimal money;
	@Column(updatable=false)
	private Date regDate;
	private Date updateDate;
	private String groupId;
}
