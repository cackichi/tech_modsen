package com.example.librarysevice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookKeeping {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnAt;
}
