package com.fnz.dto.expense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpenseDto {
    @NotBlank
    private String categoryId;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal amount;
}
