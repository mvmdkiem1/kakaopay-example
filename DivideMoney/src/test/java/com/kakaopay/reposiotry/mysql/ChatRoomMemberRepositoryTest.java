package com.kakaopay.reposiotry.mysql;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.repository.mysql.ChatRoomMemberRepository;
import com.kakaopay.repository.mysql.entity.ChatRoomMemberEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChatRoomMemberRepositoryTest {
	@Autowired
    private ChatRoomMemberRepository rep;
	
	@BeforeEach
	void setUp() throws Exception {
	}

    @Test
    public void testFindByChatRoomId() {
    	//회원 정보 조회
    	String roomId = "T100212";
    	List<ChatRoomMemberEntity> list = rep.findByChatRoomId(roomId);
    	assertThat(list, is(notNullValue()));
    }
}
