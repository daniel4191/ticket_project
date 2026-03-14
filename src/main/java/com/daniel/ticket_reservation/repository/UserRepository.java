package com.daniel.ticket_reservation.repository;

import com.daniel.ticket_reservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}