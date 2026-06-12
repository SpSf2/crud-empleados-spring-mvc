package com.example.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.model.Genero;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="empleados")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Empleado {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaAlta;

    private BigDecimal salario;
    
}
