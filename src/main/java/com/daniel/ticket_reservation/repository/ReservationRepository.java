package com.daniel.ticket_reservation.repository;

import com.daniel.ticket_reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}