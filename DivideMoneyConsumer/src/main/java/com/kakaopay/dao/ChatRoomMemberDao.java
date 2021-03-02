package com.kakaopay.dao;

import java.util.List;

import com.kakaopay.vo.ChatRoomMember;

public interface ChatRoomMemberDao {
	public List<ChatRoomMember> findChatRoomMemberByRoomId(String chatRoomId) throws Exception;
}
