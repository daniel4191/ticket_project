package com.daniel.ticket_reservation.controller;

import com.daniel.ticket_reservation.entity.Exam;
import com.daniel.ticket_reservation.entity.User;
import com.daniel.ticket_reservation.repository.ExamRepository;
import com.daniel.ticket_reservation.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    public ExamController(ExamRepository examRepository, UserRepository userRepository) {
        this.examRepository = examRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/init")
    public String initData() {
        // 1. 테스트용 가짜 회원 생성
        User user = new User();
        user.setEmail("daniel@test.com");
        user.setPassword("1234");
        user.setName("다니엘");
        userRepository.save(user); // DB에 회원 저장 (ID 1번으로 들어감)

        // 2. 테스트용 시험 데이터 생성
        Exam exam = new Exam();
        exam.setTitle("2026년 1회 정보처리기사 실기 접수");
        exam.setOpenTime(LocalDateTime.of(2026, 4, 1, 10, 0));
        exam.setMaxCapacity(100);
        exam.setCurrentCount(0);
        examRepository.save(exam); // DB에 시험 저장 (ID 1번으로 들어감)

        return "가짜 회원(다니엘)과 시험 데이터가 성공적으로 세팅되었습니다!";
    }

    @GetMapping
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }
}