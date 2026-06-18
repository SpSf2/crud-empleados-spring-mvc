package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/empleados")
@RequiredArgsConstructor
public class EmpleadoController {
    
    private final EmpleadoService empleadoService;
    private final DepartamentoService departamentoService;


    @GetMapping("/listar")
    public String listarEmpleados(Model model) {

        model.addAttribute("empleados", empleadoService.getAllEmpleados());

        return "listadoEmpleados";
    }

    //metodo para mostrar el formulario de creación de empleados
    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model) {

        //Se necesitan los Departamentos desde la capa de Servicios y lo inyectamos arriba
        model.addAttribute("departamentos", departamentoService.getAllDepartamentos());

        /*Se necesita enviar un Objeto Empleado vacio, para que se vinculen sus propiedades con cada control
        (elemento, input, select, etc) del formulario */
        model.addAttribute("empleado", new Empleado());

        return "formularioAltaModificacion";
    }
}
