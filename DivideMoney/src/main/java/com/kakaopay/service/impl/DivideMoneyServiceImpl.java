package com.kakaopay.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaopay.repository.kafka.DivideMoneyKafkaDao;
import com.kakaopay.repository.mysql.ChatRoomMemberRepository;
import com.kakaopay.repository.mysql.ChatRoomRepository;
import com.kakaopay.repository.mysql.DivideMoneyRepository;
import com.kakaopay.repository.mysql.entity.ChatRoomEntity;
import com.kakaopay.repository.mysql.entity.ChatRoomMemberEntity;
import com.kakaopay.repository.mysql.entity.DivideMoneyEntity;
import com.kakaopay.service.DivideMoneyService;
import com.kakaopay.utils.DateUtils;
import com.kakaopay.utils.TokkenUtils;
import com.kakaopay.vo.DivideMoney;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Service
public class DivideMoneyServiceImpl implements DivideMoneyService {
	@Autowired
	ChatRoomRepository chatRoomRep;
	@Autowired
	ChatRoomMemberRepository chatRoomMemberRep;
	@Autowired
	DivideMoneyRepository divideMoneyRep;
	@Autowired
	DivideMoneyKafkaDao kafkaDao;

	@Override
	public Map<String, Object> get(DivideMoney params) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			DivideMoney dm = TokkenUtils.parsingToken(params.getTokken());
			//돈을 뿌린 user id와 요청자의 동일인인지 체크한다.
			if(params.getUserId().equals(dm.getUserId())) {
				List<DivideMoneyEntity> dmList = divideMoneyRep.findByGroupIdOrderByOrderNoAsc(dm.getGroupId());

				BigDecimal money = new BigDecimal("0");
				List<HashMap<String, String>> endList = new ArrayList<HashMap<String, String>>(); 
				for(int i = 0; i < dmList.size(); i++) {
					//종료된 목록과 아직 남아 있는 돈의 총합을 구한다.
					if(!"".equals(dmList.get(i).getUserId())) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("money"		, String.valueOf(dmList.get(i).getMoney())); 	//받은 금액
						map.put("userId"	, dmList.get(i).getUserId());					//받은 사용자 id
						endList.add(map);
						
						//받아간 금액의 총액
						money = money.add(dmList.get(i).getMoney());
					}
				}
				
				result.put("regDate", 	DateUtils.getDateStr(dm.getRegDate()));	 	//뿌린 시간
				result.put("totalMoney",String.valueOf(dm.getMoney())); 			//뿌리기로 한 금액
				result.put("giveMoney", String.valueOf(money)); 					//받기 완료된 금액
				result.put("giveList", 	endList);									//받은 목록
				result.put("resultCode","200");
				result.put("resultMsg", "정상 처리 되었습니다.");
			} else {
				// error : 돈을 뿌린 사람이 아님
				result.put("resultCode", "500");
				result.put("resultMsg", "해당 내용을 확인할 권한이 없습니다.");
			}
		} catch (ExpiredJwtException ex) {
			//error : 토큰 인증 기간 만료
			result.put("resultCode", "500");
			result.put("resultMsg", "인증 기간이 만료된 Tokken 입니다.");
		} catch (SignatureException se) {
			//error : 유효하지 않은 토큰
			result.put("resultCode", "500");
			result.put("resultMsg", "유효하지 않은 Tokkne 입니다.");
		} catch (Exception e) {
			e.printStackTrace();
			//error : 유효하지 않은 토큰
			result.put("resultCode", "500");
			result.put("resultMsg", "시스템 오류로 인한 장애 발생");
		}
		return result;
	}

	@Override
	public Map<String, String> post(DivideMoney params) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			// 대화방의 정보를 조회한다.
			ChatRoomEntity chatRoom = chatRoomRep.findByChatRoomId(params.getRoomId());
			if (chatRoom != null && "LIVE".equals(chatRoom.getChatRoomStatus())) {
				// 대화방의 사용자 목록을 조회한다.
				List<ChatRoomMemberEntity> list = chatRoomMemberRep.findByChatRoomId(params.getRoomId());

				// 자신을 제외한 인원을 체크한다.
				int count = 0;
				for (int i = 0; i < list.size(); i++) {
					if (!params.getUserId().equals(list.get(i).getUserId())) count++;
					// 소유한 금액이 분배할 금액보다 적은 경우 오류 체크 (요구 사항에서 제외)
				}

				// 자신을 제외한 인원이 params.getCount 보다 많으면 kafka로 돈 분배 요청을 전송한다.
				if (count > params.getCount()) {
					// 토큰 발급 후 kafka로 돈 분배 내용 저장 요청한다.
					String tokken = TokkenUtils.createToken(params);
					kafkaDao.send(params);

					// success : 정상 처리 된 경우
					result.put("tokken", tokken);
					result.put("resultCode", "200");
					result.put("resultMsg", "정상 처리 되었습니다.");
				} else {
					// error : 대화방 보다 많은 인원을 선택한 경우
					result.put("tokken", "");
					result.put("resultCode", "500");
					result.put("resultMsg", "대화방의 참여 인원보다 많은 인원을 선택할 수 없습니다.");
				}
			} else {
				// error : 대화방이 존재하지 않는 경우
				result.put("tokken", "");
				result.put("resultCode", "500");
				result.put("resultMsg", "해당 대화방이 존재하지 않습니다.");
			}
		} catch(Exception e) {
			e.printStackTrace();
			//error : 유효하지 않은 토큰
			result.put("resultCode", "500");
			result.put("resultMsg", "시스템 오류로 인한 장애 발생");
		}

		return result;
	}

	@Override
	public Map<String, String> put(DivideMoney params) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			DivideMoney dm = TokkenUtils.parsingToken(params.getTokken());
			
			//10분 토큰 유효기간 체크
			if(DateUtils.getMinCompre(dm.getRegDate(), 10)){
				//돈을 뿌린 user id와 요청자의 동일인인지 체크한다.
				if(!params.getUserId().equals(dm.getUserId())) {
					// 대화방의 정보를 조회한다.
					ChatRoomEntity chatRoom = chatRoomRep.findByChatRoomId(params.getRoomId());
					if (chatRoom != null && "LIVE".equals(chatRoom.getChatRoomStatus())) {
						//대화방 목록에 돈 주는 사람과 돈 받는 사람이 있는지 확인한다. (돈 주는 사람도 대화방을 나가면 돈 못 받도록 처리)
						
						int member1Idx = -1; 			//돈 주는 사람의 idx
						boolean member1Check = false; 	//돈 주는 사람의 존재 여부
						
						int member2Idx = -1;			//돈 받는 사람의 idx
						boolean member2Check = false; 	//돈 받는 사람의 존재 여부
						List<ChatRoomMemberEntity> memberList = chatRoomMemberRep.findByChatRoomId(params.getRoomId());
						for(int i = 0; i < memberList.size(); i++) {
							//돈 주는 사람 체크
							if(dm.getUserId().equals(memberList.get(i).getUserId())) {
								member1Idx = i; 
								member1Check = true;
							}
							
							//돈 받는 사람 체크
							if(params.getUserId().equals(memberList.get(i).getUserId())) {
								member2Idx = i; 
								member2Check = true;
							}
						}
						
						if(member1Check == true && member2Check == true) {
							//해당 사용자가 이미 돈을 받았는지 체크한다.
							List<DivideMoneyEntity> dmList1 = divideMoneyRep.findByUserIdAndGroupIdOrderByOrderNoAsc(params.getUserId(), dm.getGroupId());
							if(dmList1.size() == 0) {
								//남아 있는 돈 뿌리기 정보가 있는지 확인한다.
								List<DivideMoneyEntity> dmList2 = divideMoneyRep.findByUserIdAndGroupIdOrderByOrderNoAsc("", dm.getGroupId());
								if(dmList2.size() > 0) {
									try {
										//돈 주는 사람이 충분한 돈이 있는지 체크한다. (요구 사항에서 제외)
										BigDecimal money = dmList2.get(0).getMoney();
										
										//돈 뿌리기 정보의 userid에 대화방 사용자의 ID, Name, update_date를 입력한다. -> 해당 분배 목록은 다음 작업에 조회되지 않는다.
										dmList2.get(0).setUserId(params.getUserId());
										dmList2.get(0).setUserName(memberList.get(member2Idx).getUserName());
										dmList2.get(0).setUpdateDate(new Date());
										divideMoneyRep.save(dmList2.get(0));
										
										//돈 주는 사람의 돈을 차감한다.
										BigDecimal tmpMoney1 = memberList.get(member1Idx).getUserMoney();
										memberList.get(member1Idx).setUserMoney(tmpMoney1.subtract(money));
										chatRoomMemberRep.save(memberList.get(member1Idx));

										//돈 받는 사람의 돈을 증가한다.
										BigDecimal tmpMoney2 = memberList.get(member2Idx).getUserMoney();
										memberList.get(member2Idx).setUserMoney(tmpMoney2.add(money));
										chatRoomMemberRep.save(memberList.get(member2Idx));
										
										// success : 정상 처리 된 경우
										result.put("resultCode", "200");
										result.put("resultMsg", String.valueOf(money) + "원을 줍줍 하였습니다. 현재 당신의 카카오 페이는 총 " + String.valueOf(memberList.get(member2Idx).getUserMoney()) + "원 입니다.");
									}catch(Exception e) {
										e.printStackTrace();
									}
								} else {
									//error : 돈 줍기가 모두 완료된 경우
									result.put("resultCode", "500");
									result.put("resultMsg", "남은 돈줍기가 없습니다.");
								}
							} else {
								//error : 돈 줍기가 모두 완료된 경우
								result.put("resultCode", "500");
								result.put("resultMsg", "이미 돈을 주으셨습니다.");
							}
							
							
						} else if(member1Check == false){
							//error : 대화방에 회원 목록에 돈 주는 사람이 존재 하지 않는 경우
							result.put("resultCode", "500");
							result.put("resultMsg", "해당 요청은 사용자의 대화방 퇴장으로 만료되었습니다.");
						} else if(member2Check == false) {
							//error : 대화방에 회원 목록에 돈 받는 사람이 존재 하지 않는 경우
							result.put("resultCode", "500");
							result.put("resultMsg", "채팅방에 존재하지 않는 사용자입니다.");
						}
					} else {
						//error : 대화방이 존재하지 않거나 status가 END인 경우
						result.put("resultCode", "500");
						result.put("resultMsg", "채팅방이 종료되어 수행할 수 없는 작업니다.");
					}
				} else {
					//error : 대화방에서 돈 뿌린 사용자가 요청한 경우
					result.put("resultCode", "500");
					result.put("resultMsg", "돈 뿌린 사람과 요청자가 동일인입니다.");
				}
			} else {
				//error : 토큰 인증 기간 만료
				result.put("resultCode", "500");
				result.put("resultMsg", "인증 기간이 만료된 Tokken 입니다.");
			}
			} catch (ExpiredJwtException ex) {
			//error : 토큰 인증 기간 만료
			result.put("resultCode", "500");
			result.put("resultMsg", "인증 기간이 만료된 Tokken 입니다.");
		} catch (SignatureException se) {
			//error : 유효하지 않은 토큰
			result.put("resultCode", "500");
			result.put("resultMsg", "유효하지 않은 Tokkne 입니다.");
		} catch (Exception e) {
			e.printStackTrace();
			//error : 유효하지 않은 토큰
			result.put("resultCode", "500");
			result.put("resultMsg", "시스템 오류로 인한 장애 발생");
		}

		return result;
	}
}
