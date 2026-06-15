package com.example.services;

import java.util.List;

import com.example.entities.Departamento;

public interface DepartamentoService {

    // Definir los metodos que se van a implementar en la clase DepartamentoServiceImpl
// que implementa esta interfaz

    Departamento saveDepartamento(Departamento departamento);

    // Metodo para obtener todos los departamentos
    List<Departamento> getAllDepartamentos();
}
