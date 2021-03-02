package com.kakaopay.repository.mysql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kakaopay.repository.mysql.entity.ChatRoomMemberEntity;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMemberEntity, Integer>{
	public List<ChatRoomMemberEntity> findByChatRoomId(String roomId);
}
