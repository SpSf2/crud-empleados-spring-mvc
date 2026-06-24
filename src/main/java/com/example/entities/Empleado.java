package com.example.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.model.Genero;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="empleados")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"telefonos", "correos"})
@Builder
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "El nombre no puede estar vacío ")
    @NotBlank(message = "El nombre no puede solo contener espacios en blanco")
    @Size(min = 4, max = 30, message = "El nombre tiene que estar entre 4 y 30 caracteres")
    @Pattern(regexp = "^([A-ZÁÉÍÓÚÑ]{1}[a-záéíóúñ]+(\s)?)+$", message = "Sin espacios en blanco al inicio," +
                                                    "La Primera letra en mayuscula, sin caracteres especiales")
    private String nombre;

    @NotNull(message = "El Primer Apellido no puede estar vacío ")
    @NotBlank(message = "El Primer Apellido no puede solo contener espacios en blanco")
    @Size(min = 4, max = 30, message = "El Primer Apellido tiene que estar entre 4 y 30 caracteres")
    @Pattern(regexp = "^([A-ZÁÉÍÓÚÑ]{1}[a-záéíóúñ]+(\s)?)+$", message = "La Primera letra en mayuscula," +
                                                                    "sin caracteres especiales")
    private String primerApellido;

    /* @NotBlank(message = "El Segundo Apellido no puede solo contener espacios en blanco")
    @Size(max = 45, message = "El Segundo Apellido  no debe superar los 45 caracteres")
    @Pattern(regexp = "^([A-ZÁÉÍÓÚÑ]{1}[a-záéíóúñ]+(\s)?)+$", message = "La Primera letra en mayuscula," +
                                                                    "sin caracteres especiales") */ //Solucionar esto!!! aqui y en el formulario
    private String segundoApellido;
    
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @PastOrPresent(message = "La fecha de alta tiene que ser igual o anterior a la fecha actual")
    private LocalDate fechaAlta;

    @DecimalMin(value = "0", message = "El salario no puede ser negativo")
    private BigDecimal salario;

    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento departamento;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "empleado")
    @Builder.Default
    private Set<Telefono> telefonos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "empleado")
    @Builder.Default
    private Set<Correo> correos = new HashSet<>();
    
    private String foto;
    
}
