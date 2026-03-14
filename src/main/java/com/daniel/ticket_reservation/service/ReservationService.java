package com.daniel.ticket_reservation.service;

import com.daniel.ticket_reservation.entity.Exam;
import com.daniel.ticket_reservation.entity.Reservation;
import com.daniel.ticket_reservation.entity.User;
import com.daniel.ticket_reservation.repository.ExamRepository;
import com.daniel.ticket_reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final ExamRepository examRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(ExamRepository examRepository, ReservationRepository reservationRepository) {
        this.examRepository = examRepository;
        this.reservationRepository = reservationRepository;
    }

    // 🔥 변경된 부분: examId와 함께 userId도 받습니다!
    @Transactional 
    public String reserve(Long examId, Long userId) { 
        
        Exam exam = examRepository.findByIdWithPessimisticLock(examId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시험입니다."));

        if (exam.getCurrentCount() >= exam.getMaxCapacity()) {
            throw new IllegalStateException("예약이 마감되었습니다.");
        }

        exam.setCurrentCount(exam.getCurrentCount() + 1);
        examRepository.save(exam);

        Reservation reservation = new Reservation();
        reservation.setExam(exam);
        reservation.setStatus("COMPLETED");
        
        // 🔥 변경된 부분: 무조건 1번이 아니라, 받아온 userId를 넣습니다.
        User tempUser = new User(); 
        tempUser.setId(userId); 
        reservation.setUser(tempUser); 

        reservationRepository.save(reservation);

        return "예약 성공! 현재 예약 인원: " + exam.getCurrentCount() + " / " + exam.getMaxCapacity();
    }
}