package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.CorreoDao;
import com.example.entities.Correo;
import com.example.entities.Empleado;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CorreoServiceImpl implements CorreoService{

    private final CorreoDao correoDao;


    @Override
    public Correo saveCorreo(Correo correo) {
        
        return correoDao.save(correo);
    }

    @Override
    public List<Correo> getAllCorreos() {
        
        return correoDao.findAll();
    }

    @Override
    public boolean existsByEmpleado(Empleado empleado) {
        
        return correoDao.existsByEmpleado(empleado);
    }

    @Override
    @Transactional
    public void deleteByEmpleado(Empleado empleado) {
        
        correoDao.deleteByEmpleado(empleado);
    }

    @Override
    public List<Correo> findByEmpleado(Empleado empleado) {
        
        return correoDao.findByEmpleado(empleado);
    }

}
