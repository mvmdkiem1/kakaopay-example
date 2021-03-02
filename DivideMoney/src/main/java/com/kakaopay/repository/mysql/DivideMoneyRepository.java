package com.kakaopay.repository.mysql;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kakaopay.repository.mysql.entity.DivideMoneyEntity;

@Repository
public interface DivideMoneyRepository extends JpaRepository<DivideMoneyEntity, Integer>{
	public List<DivideMoneyEntity> findByGroupIdOrderByOrderNoAsc(String groupId);
	public List<DivideMoneyEntity> findByUserIdAndGroupIdOrderByOrderNoAsc(String userId, String groupId);
}
