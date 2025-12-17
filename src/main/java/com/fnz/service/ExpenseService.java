package com.fnz.service;

import com.fnz.dto.ExpenseDto;
import com.fnz.entity.Expense;
import com.fnz.entity.User;
import com.fnz.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryService categoryService;

    public Expense create(ExpenseDto dto, User user) {
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setCategory(categoryService.findById(dto.getCategoryId()));
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        return expenseRepository.save(expense);
    }

    public List<Expense> findByUser(User user) {
        return expenseRepository.findByUser(user);
    }

    public Expense findById(String id) {
        return expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    public Expense update(String id, ExpenseDto dto, User user) {
        Expense expense = findById(id);
        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        expense.setCategory(categoryService.findById(dto.getCategoryId()));
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        return expenseRepository.save(expense);
    }

    public void delete(String id, User user) {
        Expense expense = findById(id);
        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        expenseRepository.deleteById(id);
    }
}
