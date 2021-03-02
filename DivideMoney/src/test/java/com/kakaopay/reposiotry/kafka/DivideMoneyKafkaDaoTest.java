package com.kakaopay.reposiotry.kafka;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.repository.kafka.DivideMoneyKafkaDao;
import com.kakaopay.vo.DivideMoney;

@RunWith(SpringRunner.class)
@SpringBootTest
class DivideMoneyKafkaDaoTest {
	@Autowired
    private DivideMoneyKafkaDao dao;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
    @Test
    public void testSend() {
    	//kafka 내용 전달
    	DivideMoney dm = new DivideMoney();
    	dm.setCount(3);
    	dm.setMoney(new BigDecimal(200000));
    	dm.setUserId("mvmdkiem@naver.com");
    	dm.setRoomId("T100212");
    	dm.setRegDate(new Date());
    	dm.setGroupId(dm.getUserId() + "-" + dm.getRoomId() + "-" + dm.getRegDate());
    	
    	dao.send(dm);
    }
}
