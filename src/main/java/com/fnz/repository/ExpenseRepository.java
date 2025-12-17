package com.fnz.repository;

import com.fnz.entity.Expense;
import com.fnz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
    List<Expense> findByUserId(String userId);
    List<Expense> findByUserIdAndCreatedAtBetween(String userId, LocalDateTime start, LocalDateTime end);
    List<Expense> findByUser(User user);
}
