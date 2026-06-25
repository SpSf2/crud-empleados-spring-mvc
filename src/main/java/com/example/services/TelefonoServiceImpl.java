package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.TelefonoDao;
import com.example.entities.Empleado;
import com.example.entities.Telefono;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TelefonoServiceImpl implements TelefonoService{

    private final TelefonoDao telefonoDao;

    @Override
    public List<Telefono> getAllTelefonos() {
        
        return telefonoDao.findAll();
    
    }

    @Override
    public Telefono saveTelefono(Telefono telefono) {
        
        return telefonoDao.save(telefono);
    }

    @Override
    public boolean existsByEmpleado(Empleado empleado) {
        
        return telefonoDao.existsByEmpleado(empleado);
    }

    @Override
    @Transactional
    public void deleteByEmpleado(Empleado empleado) {
        
        telefonoDao.deleteByEmpleado(empleado);
    }

    @Override
    public List<Telefono> findByEmpleado(Empleado empleado) {
        
        return telefonoDao.findByEmpleado(empleado);
    
    }

    
}