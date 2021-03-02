package com.kakaopay.repository.kafka;

import com.kakaopay.vo.DivideMoney;

public interface DivideMoneyKafkaDao {
	public void send(DivideMoney params); 
    
}
