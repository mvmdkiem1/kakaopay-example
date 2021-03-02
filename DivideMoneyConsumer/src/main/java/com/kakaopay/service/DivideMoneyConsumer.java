package com.kakaopay.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.kakaopay.dao.ChatRoomDao;
import com.kakaopay.dao.ChatRoomMemberDao;
import com.kakaopay.dao.DivideMoneyDao;
import com.kakaopay.utils.BigDecimalUtils;
import com.kakaopay.vo.ChatRoom;
import com.kakaopay.vo.ChatRoomMember;
import com.kakaopay.vo.DivideMoney;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@MapperScan(basePackages="com.kakaopay.dao")
public class DivideMoneyConsumer {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ChatRoomDao chatRoomDao;
	
	@Autowired
	ChatRoomMemberDao chatRoomMemberDao;
	
	@Autowired
	DivideMoneyDao divideMoneyDao;
	
	@KafkaListener(topics = "kakaopay", groupId = "kakaopay-consumer-group")
	public void consumer(String message) throws IOException {
		logger.info(String.format("Consumed message : %s", message));
		
		try {
			//message를 객체로 변환한다.
			Gson gson = new Gson();
			DivideMoney params = gson.fromJson(message, DivideMoney.class);
			System.out.println(params);
			
			//MongoDB에서 대화방 상태를 조회
			ChatRoom ch = chatRoomDao.findChatRoomByRoomId(params.getRoomId());
			
			//MongoDB에서 대화방 상태가 Live인지 확인
			if(ch != null && "LIVE".equals(ch.getChatRoomStatus())){
				
				//MongoDB에서 대화방 인원 목록 조회
				List<ChatRoomMember> memberList = chatRoomMemberDao.findChatRoomMemberByRoomId(params.getRoomId());
				
				//대화방 인원 -1이 분배할 인원보다 많거나 같은지 확인 
				if((memberList.size() - 1) >= params.getCount())  {
					
					//돈 균등 분배
					List<BigDecimal> moneyList = BigDecimalUtils.getRanBigDecimalList(params);
					List<DivideMoney> addList = new ArrayList<DivideMoney>();
					for(int i = 0; i < moneyList.size(); i++) {
						DivideMoney dm = new DivideMoney();
						dm.setOrderNo(i);
						dm.setMoney(moneyList.get(i));
						dm.setRoomId(params.getRoomId());
						dm.setUserId("");
						dm.setRegDate(params.getRegDate());
						dm.setGroupId(params.getGroupId());
						addList.add(dm);
					}
					
					System.out.println(addList);
					for(int i = 0; i < addList.size(); i++) {
						divideMoneyDao.addDivideMoney(addList.get(i));
					}
				}
			}
 		} catch (Exception e) {
			//오류 처리
			e.printStackTrace();
		}
	}
}
