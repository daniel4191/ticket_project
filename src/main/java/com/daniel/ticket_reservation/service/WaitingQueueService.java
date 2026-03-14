package com.daniel.ticket_reservation.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WaitingQueueService {

    private final RedisTemplate<String, String> redisTemplate;
    private final String QUEUE_KEY = "exam_queue"; // 대기열 이름

    public WaitingQueueService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 1. 대기열에 사용자 추가 (줄 세우기)
    public Long addQueue(Long userId) {
        long now = System.currentTimeMillis();
        // Redis의 Sorted Set을 사용하여 시간순으로 유저 저장
        redisTemplate.opsForZSet().add(QUEUE_KEY, userId.toString(), now);
        
        // 내 앞의 대기 순번 반환
        return redisTemplate.opsForZSet().rank(QUEUE_KEY, userId.toString());
    }

    // 2. 내 대기 순번 조회하기
    public Long getRank(Long userId) {
        return redisTemplate.opsForZSet().rank(QUEUE_KEY, userId.toString());
    }
}