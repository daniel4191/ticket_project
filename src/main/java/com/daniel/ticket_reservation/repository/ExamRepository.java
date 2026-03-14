package com.daniel.ticket_reservation.repository;

import com.daniel.ticket_reservation.entity.Exam;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // 추가됨!

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from Exam e where e.id = :id")
    // 🔥 @Param("id") 가 반드시 있어야 최신 버전에서 에러가 안 납니다!
    Optional<Exam> findByIdWithPessimisticLock(@Param("id") Long id);
}