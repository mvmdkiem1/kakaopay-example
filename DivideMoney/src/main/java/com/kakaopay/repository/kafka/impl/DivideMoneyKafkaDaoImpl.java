package com.kakaopay.repository.kafka.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.kakaopay.repository.kafka.DivideMoneyKafkaDao;
import com.kakaopay.vo.DivideMoney;

@Service
public class DivideMoneyKafkaDaoImpl implements DivideMoneyKafkaDao {
    private static final String TOPIC = "kakaopay";
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    public DivideMoneyKafkaDaoImpl(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
	@Override
	public void send(DivideMoney params) {
		Gson gson = new Gson();
		String message = gson.toJson(params);
		this.kafkaTemplate.send(TOPIC, message);
	}
}
