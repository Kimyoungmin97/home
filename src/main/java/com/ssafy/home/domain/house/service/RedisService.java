package com.ssafy.home.domain.house.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final StringRedisTemplate redisTemplate;
	
	private static String key = "";
	private static final int MAX_RECENT = 10;

	// 검색어 저장 (점수 증가)
    public void saveKeyword(String keyword) {
    	key = "search:keywords";
        redisTemplate.opsForZSet().incrementScore(key, keyword, 1);
    }

    // 인기 검색어 TOP N 가져오기
    public Set<ZSetOperations.TypedTuple<String>> getTopKeywords(int topN) {
    	key = "search:keywords";
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, topN - 1);
    }
    
    public void saveRecentKeyword(String userId, String keyword) {
        key = "search:recent:" + userId;

        // 중복 방지: 먼저 제거
        redisTemplate.opsForList().remove(key, 0, keyword);

        // 앞에 추가
        redisTemplate.opsForList().leftPush(key, keyword);

        // 10개만 유지
        redisTemplate.opsForList().trim(key, 0, MAX_RECENT - 1);
    }

    public List<String> getRecentKeywords(String userId) {
        key = "search:recent:" + userId;
        return redisTemplate.opsForList().range(key, 0, MAX_RECENT - 1);
    }
}
