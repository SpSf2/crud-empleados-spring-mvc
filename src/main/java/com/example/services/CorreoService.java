package com.example.services;

import java.util.List;

import com.example.entities.Correo;
import com.example.entities.Empleado;

public interface CorreoService {

    // Definir los metodos que se van a implementar en la clase CorreoServiceImpl
// que implementa esta interfaz

    Correo saveCorreo(Correo correo);

    // Metodo para obtener todos los correos
    List<Correo> getAllCorreos();

    boolean existsByEmpleado(Empleado empleado);

    // Este metodo elimina un correo

    void deleteByEmpleado(Empleado empleado);

    // Este metodo muestra los correos de un empleado

    List<Correo> findByEmpleado(Empleado empleado);
}
