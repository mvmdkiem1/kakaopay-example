package com.kakaopay.dao;

import com.kakaopay.vo.ChatRoom;

public interface ChatRoomDao {
	public ChatRoom findChatRoomByRoomId(String chatRoomId) throws Exception;
}
