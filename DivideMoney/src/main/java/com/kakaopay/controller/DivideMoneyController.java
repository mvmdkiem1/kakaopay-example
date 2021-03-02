package com.kakaopay.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.controller.model.RequestParams;
import com.kakaopay.service.DivideMoneyService;
import com.kakaopay.vo.DivideMoney;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/api")
public class DivideMoneyController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	DivideMoneyService service;
	
	@GetMapping(value = "divide", produces = "application/json")
	@ApiOperation(value = "돈 뿌리기 - 목록 조회", tags = "DivideMoney")
	public Map<String, Object> get(HttpServletRequest req, @RequestBody RequestParams params) throws Exception {
		DivideMoney dm = new DivideMoney();
		dm.setUserId(req.getHeader("X-USER-ID"));
		dm.setRoomId(req.getHeader("X-ROOM-ID"));
		dm.setTokken(params.getTokken());
		dm.setRegDate(new Date());

		return service.get(dm);
	}
	
	@PostMapping(value = "divide", produces = "application/json")
	@ApiOperation(value = "돈 뿌리기 - 생성", tags = "DivideMoney")
	public Map<String, String> post(HttpServletRequest req, @RequestBody RequestParams params) throws Exception {
		DivideMoney dm = new DivideMoney();
		dm.setUserId(req.getHeader("X-USER-ID"));
		dm.setRoomId(req.getHeader("X-ROOM-ID"));
		dm.setMoney(new BigDecimal(String.valueOf(params.getMoney())));
		dm.setCount(params.getCount());
		dm.setRegDate(new Date());
		dm.setGroupId(dm.getUserId() + "-" + dm.getRoomId() + "-" + dm.getRegDate());
		
		return service.post(dm);
	}

	@PutMapping(value = "divide", produces = "application/json")
	@ApiOperation(value = "돈 뿌리기 - 돈 받기", tags = "DivideMoney")
	public Map<String, String> put(HttpServletRequest req, @RequestBody RequestParams params) throws Exception {
		DivideMoney dm = new DivideMoney();
		dm.setUserId(req.getHeader("X-USER-ID"));
		dm.setRoomId(req.getHeader("X-ROOM-ID"));
		dm.setTokken(params.getTokken());
		dm.setRegDate(new Date());

		return service.put(dm);
	}
}
