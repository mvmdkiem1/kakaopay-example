package com.kakaopay.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kakaopay.vo.DivideMoney;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokkenUtils {
	final static String key = "divideMoneyKey";

	public static String createToken(DivideMoney params) {
		Map<String, Object> headers = new HashMap<String, Object>();

		Map<String, Object> payloads = new HashMap<>();
		payloads.put("X-USER-ID", params.getUserId());
		payloads.put("X-ROOM-ID", params.getRoomId());
		payloads.put("money", params.getMoney());
		payloads.put("count", params.getCount());
		payloads.put("regdate", params.getRegDate());
		payloads.put("groupId", params.getGroupId());
		
		// 토큰 유효 시간 (7일)
		Long expiredTime = 1000L * 60 * 60 * 24 * 7;

		Date ext = new Date(); // 토큰 만료 시간
		ext.setTime(ext.getTime() + expiredTime);

		return Jwts.builder().setHeader(headers) // Headers 설정
				.setClaims(payloads) // Claims 설정
				.setSubject("divideMoney") // 토큰 용도
				.setExpiration(ext) // 토큰 만료 시간 설정
				.signWith(SignatureAlgorithm.HS256, key.getBytes()) // HS256과 Key로 Sign
				.compact(); // 토큰 생성
	}

	public static DivideMoney parsingToken(String token) throws InterruptedException, ExpiredJwtException {
		Map<String, String> result = new HashMap<String, String>();
		Claims body = Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody();

		DivideMoney dm = new DivideMoney();
		dm.setCount(Integer.parseInt(String.valueOf(body.get("count"))));
		dm.setMoney(new BigDecimal(String.valueOf(body.get("money"))));
		dm.setUserId(String.valueOf(body.get("X-USER-ID")));
		dm.setRoomId(String.valueOf(body.get("X-ROOM-ID")));
		dm.setRegDate(new Date(Long.parseLong(String.valueOf(body.get("regdate")))));
		dm.setGroupId(String.valueOf(body.get("groupId")));
		
		return dm;
	}
}
