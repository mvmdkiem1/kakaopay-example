package com.kakaopay.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.vo.DivideMoney;

@RunWith(SpringRunner.class)
@SpringBootTest
class DivideMoneyServiceImplTest {
	@Autowired
    private DivideMoneyService service;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	String oldTokken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaXZpZGVNb25leSIsIm1vbmV5IjoyMDAwMCwiZ3JvdXBJZCI6Im12bWRraWVtQG5hdmVyLmNvbS1UMTAwMjEyLVR1ZSBNYXIgMDIgMTA6NTk6NTMgS1NUIDIwMjEiLCJjb3VudCI6MywicmVnZGF0ZSI6MTYxNDY1MDM5MzA3MiwiWC1VU0VSLUlEIjoibXZtZGtpZW1AbmF2ZXIuY29tIiwiZXhwIjoxNjE1MjU1MTkzLCJYLVJPT00tSUQiOiJUMTAwMjEyIn0.26uaUvp6PGdxpk4OKZZIDiOACImDbWN58ddUDJeM9E8";
	String newTokken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaXZpZGVNb25leSIsIm1vbmV5IjoyMDAwMCwiZ3JvdXBJZCI6Im12bWRraWVtQG5hdmVyLmNvbS1UMTAwMjEyLVR1ZSBNYXIgMDIgMTU6MDU6NDggS1NUIDIwMjEiLCJjb3VudCI6MywicmVnZGF0ZSI6MTYxNDY2NTE0ODkxMiwiWC1VU0VSLUlEIjoibXZtZGtpZW1AbmF2ZXIuY29tIiwiZXhwIjoxNjE1MjY5OTQ4LCJYLVJPT00tSUQiOiJUMTAwMjEyIn0.S57E2xvrpbQXwhbAH4BqpwyHS19YoiND-LcwPdS87t0";

	@Test
	void testGet() {
		//조회 service 테스트 케이스
    	DivideMoney dm = new DivideMoney();
    	dm.setCount(3);
    	dm.setMoney(new BigDecimal(200000));
    	dm.setUserId("mvmdkiem@naver.com");
    	dm.setRoomId("T100212");
    	dm.setRegDate(new Date());
    	dm.setGroupId(dm.getUserId() + "-" + dm.getRoomId() + "-" + dm.getRegDate());
    	dm.setTokken(newTokken);
		service.get(dm);
	}
	
	@Test
	void testPost() {
		//돈 뿌리기 생성 service 테스트 케이스
    	DivideMoney dm = new DivideMoney();
    	dm.setCount(3);
    	dm.setMoney(new BigDecimal(200000));
    	dm.setUserId("mvmdkiem@naver.com");
    	dm.setRoomId("T100212");
    	dm.setRegDate(new Date());
    	dm.setGroupId(dm.getUserId() + "-" + dm.getRoomId() + "-" + dm.getRegDate());
    	dm.setTokken(newTokken);
		service.post(dm);
	}
	
	@Test
	void testPut() {
		//돈 줍기 생성 service 테스트 케이스
    	DivideMoney dm = new DivideMoney();
    	dm.setCount(3);
    	dm.setMoney(new BigDecimal(200000));
    	dm.setUserId("test1@naver.com");
    	dm.setRoomId("T100212");
    	dm.setRegDate(new Date());
    	dm.setGroupId(dm.getUserId() + "-" + dm.getRoomId() + "-" + dm.getRegDate());
    	dm.setTokken(newTokken);
		service.put(dm);
	}


}
