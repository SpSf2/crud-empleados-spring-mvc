package com.example.services;

import com.example.entities.Telefono;

public interface TelefonoService {

    // Definir los metodos que se van a implementar en la clase TelefonoServiceImpl
// que implementa esta interfaz

    Telefono saveTelefono(Telefono telefono);
    // Metodo para obtener todos los telefonos
    List<Telefono> getAllTelefonos();
    // Metodo para obtener un telefono por su id
    Telefono getTelefonoById(int id);
    // Metodo para persistir (guardar) un telefono
    Telefono saveTelefono(Telefono telefono);

    // Metodo para actualizar un telefono
    Telefono updateTelefono(Telefono telefono);
    // Metodo para eliminar un telefono por su id
    void deleteTelefono(int id);
    // Metodo que elimina un telefono recibiendo el objeto telefono
    void deleteTelefono(Telefono telefono);
    
}
