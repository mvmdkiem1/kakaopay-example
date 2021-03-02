package com.kakaopay.reposiotry.mysql;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.repository.mysql.ChatRoomRepository;
import com.kakaopay.repository.mysql.entity.ChatRoomEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChatRoomRepositoryTest {
	@Autowired
	ChatRoomRepository rep;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void testFindByChatRoomId() {
    	//대화방 정보 조회
    	String roomId = "T100212";
    	ChatRoomEntity room = rep.findByChatRoomId(roomId);
    	assertThat(room, is(notNullValue()));
	}
}
