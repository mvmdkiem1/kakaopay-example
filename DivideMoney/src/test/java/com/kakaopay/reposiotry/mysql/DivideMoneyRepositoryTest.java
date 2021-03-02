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

import com.kakaopay.repository.mysql.DivideMoneyRepository;
import com.kakaopay.repository.mysql.entity.DivideMoneyEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DivideMoneyRepositoryTest {
	@Autowired
	DivideMoneyRepository rep;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFindByGroupIdOrderByOrderNoAsc() {
    	//전체 돈 뿌리기 정보 조회
    	String groupId = "mvmdkiem@naver.com-T100212-Tue Mar 02 15:05:48 KST 2021";
    	List<DivideMoneyEntity> list = rep.findByGroupIdOrderByOrderNoAsc(groupId);
    	assertThat(list, is(notNullValue()));
	}
	
	@Test
	void testFindByUserIdAndGroupIdOrderByOrderNoAsc() {
    	//비어 있는 돈 뿌리기 정보 조회
		String userId = "";
    	String groupId = "mvmdkiem@naver.com-T100212-Tue Mar 02 15:05:48 KST 2021";
    	List<DivideMoneyEntity> list = rep.findByUserIdAndGroupIdOrderByOrderNoAsc(userId, groupId);
    	assertThat(list, is(notNullValue()));
	}
}
