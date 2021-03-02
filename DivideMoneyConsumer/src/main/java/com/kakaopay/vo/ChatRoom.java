package com.kakaopay.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @AllArgsConstructor @NoArgsConstructor 
public class ChatRoom {
	private int _id;
	private String chatRoomId;
	private String chatRoomTitle;
	private String chatRoomStatus; 
}
