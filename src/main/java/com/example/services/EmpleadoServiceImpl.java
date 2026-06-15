package com.example.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.dao.EmpleadoDao;
import com.example.entities.Empleado;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    /* Esta clase necesita del DAO para implementar todos sus metodos.
* Anteriormente la inyeccion de dependencias tenia lugar a traves
* de la anotacion @Autowire de Spring, pero desde un tiempo
* se ha llegado a la conclusion que la inyeccion de dependencia 
* por constructor es mas eficiente.
* 
* Y si utilizamos el lombok, para que se inyecte una dependencia por constructor
* solamente hay que agregarle el modificador final */

    //Inyectamos la capa Dao
    private final EmpleadoDao empleadoDao;

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoDao.findAll();
    }

    @Override
    public Empleado getEmpleadoById(int id) {
        return empleadoDao.findById(id).orElseThrow(() -> 
            new RuntimeException("Empleado no encontrado"));
    }

    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        
        return empleadoDao.save(empleado);
    }

    @Override
    public Empleado updateEmpleado(Empleado empleado) {
        
        return empleadoDao.save(empleado);
    }

    @Override
    public void deleteEmpleado(int id) {
        
        empleadoDao.deleteById(id);
    }

    @Override
    public void deleteEmpleado(Empleado empleado) {
        
        empleadoDao.delete(empleado);
    }

}
