package com.kakaopay.repository.mysql.entity;

import java.math.BigDecimal;

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
@Table(name="chatroommember")
public class ChatRoomMemberEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int _id;
	private String chatRoomId;
	private String userId;
	private String userName;
	private BigDecimal userMoney;
}
