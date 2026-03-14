package com.daniel.ticket_reservation.controller;

import com.daniel.ticket_reservation.service.ReservationService;
import com.daniel.ticket_reservation.service.WaitingQueueService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final WaitingQueueService waitingQueueService; // 🔥 대기열 서비스 추가

    public ReservationController(ReservationService reservationService, WaitingQueueService waitingQueueService) {
        this.reservationService = reservationService;
        this.waitingQueueService = waitingQueueService;
    }

    // 1. 큐넷처럼 대기열에 먼저 진입하기
    @GetMapping("/enqueue/{userId}")
    public String enqueue(@PathVariable Long userId) {
        Long rank = waitingQueueService.addQueue(userId);
        return "대기열 진입 성공! 당신의 현재 대기 순번은 " + (rank + 1) + "번 입니다.";
    }

    @GetMapping("/reserve/{examId}")
    public String reserveTicket(@PathVariable Long examId) {
        try {
            return reservationService.reserve(examId, 1L);
        } catch (Exception e) {
            return "예약 실패: " + e.getMessage();
        }
    }
}