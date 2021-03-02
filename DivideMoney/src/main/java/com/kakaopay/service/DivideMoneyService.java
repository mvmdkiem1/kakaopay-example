package com.kakaopay.service;

import java.util.Map;

import com.kakaopay.vo.DivideMoney;

public interface DivideMoneyService {
	public Map<String, Object> get(DivideMoney params);
	
	public Map<String, String> post(DivideMoney params);
	
	public Map<String, String> put(DivideMoney params);
}
