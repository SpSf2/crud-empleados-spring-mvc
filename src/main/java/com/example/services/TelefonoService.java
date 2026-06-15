package com.example.services;

import java.util.List;

import com.example.entities.Empleado;
import com.example.entities.Telefono;

public interface TelefonoService {

    // Definir los metodos que se van a implementar en la clase TelefonoServiceImpl
// que implementa esta interfaz

    
    // Metodo para obtener todos los telefonos
    List<Telefono> getAllTelefonos();
    
    // Metodo para persistir (guardar) un telefono
    Telefono saveTelefono(Telefono telefono);

   boolean existsByEmpleado(Empleado empleado);

   void deleteByEmpleado(Empleado empleado);

   List<Telefono> findByEmpleado(Empleado empleado);


    
}
