package com.fnz.controller;

import com.fnz.dto.expense.ExpenseDto;
import com.fnz.entity.Expense;
import com.fnz.entity.User;
import com.fnz.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> create(@Valid @RequestBody ExpenseDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(expenseService.create(dto, user));
    }

    @GetMapping
    public ResponseEntity<List<Expense>> findAllForUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(expenseService.findByUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findById(@PathVariable String id) {
        return ResponseEntity.ok(expenseService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> update(@PathVariable String id, @Valid @RequestBody ExpenseDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(expenseService.update(id, dto, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        expenseService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}