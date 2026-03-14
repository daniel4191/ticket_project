package com.daniel.ticket_reservation;

import com.daniel.ticket_reservation.entity.Exam;
import com.daniel.ticket_reservation.entity.User;
import com.daniel.ticket_reservation.repository.ExamRepository;
import com.daniel.ticket_reservation.repository.UserRepository;
import com.daniel.ticket_reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TicketReservationApplicationTests {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private UserRepository userRepository;

    private Long testExamId;
    private Long testUserId; // 🔥 추가됨: 테스트할 때 쓸 유저 번호를 저장할 공간

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setEmail("tester_" + Math.random() + "@test.com");
        user.setPassword("1234");
        user.setName("테스터");
        // 🔥 추가됨: 유저를 DB에 저장하고, 발급된 진짜 ID를 가져와서 기억해둡니다.
        testUserId = userRepository.save(user).getId();

        Exam exam = new Exam();
        exam.setTitle("동시성 폭파 테스트용 시험");
        exam.setOpenTime(LocalDateTime.now());
        exam.setMaxCapacity(200);
        exam.setCurrentCount(0); 
        testExamId = examRepository.save(exam).getId();
    }

    @Test
    public void testConcurrentReservations() throws InterruptedException {
        int threadCount = 100; 
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    // 🔥 핵심: 기억해둔 진짜 시험번호와 진짜 유저번호를 둘 다 넘겨줍니다!
                    reservationService.reserve(testExamId, testUserId);
                } catch (Exception e) {
                    System.err.println("❌ 예약 실패 사유: " + e.getMessage());
                } finally {
                    latch.countDown(); 
                }
            });
        }
        latch.await(); 

        Exam exam = examRepository.findById(testExamId).orElseThrow();
        System.out.println("==========================================");
        System.out.println("🔥 100명 동시 요청 결과, 실제 예약된 인원: " + exam.getCurrentCount());
        System.out.println("==========================================");

        assertEquals(100, exam.getCurrentCount());
    }
}