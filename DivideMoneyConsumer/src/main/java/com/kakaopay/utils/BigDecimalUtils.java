package com.kakaopay.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kakaopay.vo.DivideMoney;

public class BigDecimalUtils {
	public static List<BigDecimal> getRanBigDecimalList(DivideMoney params){
		int cnt = params.getCount();
		BigDecimal total = params.getMoney();
		
		BigDecimal avg = (total.divide(new BigDecimal(100))).multiply(new BigDecimal(5));
		
		float max = Float.parseFloat(String.valueOf(avg));
		float min = Float.parseFloat(String.valueOf(avg)) * -1;

		BigDecimal ran = new BigDecimal(Math.random() * (max - min) + min);
		ran = ran.setScale(0, BigDecimal.ROUND_FLOOR);
		
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		for (int i = cnt; i > 0; i--) {
			if(i == cnt) {
				BigDecimal temp = total.divide(new BigDecimal(i), BigDecimal.ROUND_FLOOR).add(ran);
				total = total.subtract(temp);
				list.add(temp);
			} else {
				BigDecimal temp = total.divide(new BigDecimal(i), BigDecimal.ROUND_FLOOR);
				total = total.subtract(temp);
				list.add(temp);
			}
		}
		
		return list;
	}
}
