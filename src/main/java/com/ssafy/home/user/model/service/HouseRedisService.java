package com.ssafy.home.user.model.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

//@Service
//@RequiredArgsConstructor
public class HouseRedisService {
//	
//	private final RedisTemplate<String, Object> redisTemplate;
//	
//	public void saveToRedis(House house) {
//	    	String key = "house:" + house.getAptNm().replaceAll("\\s+", "");
//	    	redisTemplate.opsForValue().set(key, house);
//	    	
//	        String aptSetKey = "aptNm:" + house.getId();
//	        redisTemplate.opsForSet().add(aptSetKey, key);
//	        
//	        
//	        Object saved = redisTemplate.opsForValue().get(key);
//
//	        if (saved instanceof House) {
//	            House savedHouse = (House) saved;
//	            System.out.println("✅ Redis에 저장된 거래 정보:");
//	            System.out.println(savedHouse);
//	        } else {
//	            System.out.println("❌ 해당 ID의 데이터가 Redis에 없습니다.");
//	        }
//
//	        // 저장된 Set 목록 확인도 가능
//	        Set<Object> keys = redisTemplate.opsForSet().members(aptSetKey);
//	        System.out.println("📌 해당 아파트 Set에 저장된 key 목록: " + keys);
//	}
//	
//	public String generateId(House h) {
//		return String.format("%s_%s%02d%02d_%s_%s_%s_%s",
//		            h.getAptNm().replaceAll("\\s+", ""),
//		            h.getDealYear(), Integer.parseInt(h.getDealMonth()), Integer.parseInt(h.getDealDay()),
//		            h.getExcluUseAr().replace(".", ""),
//		            h.getFloor(), h.getAptDong(), h.getJibun());
//    }
}
