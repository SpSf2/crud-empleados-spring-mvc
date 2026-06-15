package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Correo;
import com.example.entities.Empleado;
import java.util.List;



public interface CorreoDao extends JpaRepository<Correo, Integer>{

    //Este metodo muestra si un empleado tiene correo
    boolean existsByEmpleado(Empleado empleado);

    //Este metodo elimina un correo
    void deleteByEmpleado(Empleado empleado);

    //Este metodo muestra los correos de un empleado
    List<Correo> findByEmpleado(Empleado empleado);
}
