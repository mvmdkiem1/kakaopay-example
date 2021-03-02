package com.kakaopay.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kakaopay.repository.mysql.entity.ChatRoomEntity;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Integer>{
	public ChatRoomEntity findByChatRoomId(String roomId);
}
