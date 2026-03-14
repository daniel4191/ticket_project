package com.daniel.ticket_reservation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity // 이 클래스가 DB 테이블과 매칭됨을 선언
@Table(name = "exams") // DB에 생성될 테이블 이름
@Getter
@Setter
public class Exam {

    @Id // 이 컬럼이 기본키(PK)임을 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment 설정
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false)
    private LocalDateTime openTime;

    @Column(nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    private Integer currentCount = 0; // 초기값 0
}