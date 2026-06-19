package com.example.controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
@RequestMapping("/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private static final Logger LOG = Logger.getLogger("EmpleadoController");
    
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

    /*Metodo para recibir los datos del formulario de Alta de Empleado*/
    @PostMapping("/persistir")     
    public String procesarFormuarioaltaModificacion(@ModelAttribute Empleado empleado, 
        @RequestParam String numerosTelefono,
        @RequestParam String dircorreos) {

        LOG.info("Objeto Empleado Recibido");
        LOG.info(empleado.toString());
        LOG.info("Numeros de Telefono recibidos: " + numerosTelefono);
        LOG.info("Direcciones de Correo recibidas: " + dircorreos);

        //Hay que procesar los datos de los telefonos y correos que vienen en un String
		/*separados por comas y convertirlos en listas de objetos Telefono y Correo para
		 * luego agregarlos al objeto Empleado antes de persistirlo en la DB */
		
		Set<Telefono> telefonos = new HashSet<Telefono>();
		
		if(!numerosTelefono.isEmpty()&& !numerosTelefono.isBlank() ) {
			
			String[] arrayNumerosTelefono = numerosTelefono.split(";");
			List<String> listadoNumeros = Arrays.asList(arrayNumerosTelefono);
                
        listadoNumeros.forEach(numero -> {
            telefonos.add(Telefono.builder().numero(numero).empleado(empleado).build());
		    });
        }
			empleado.setTelefonos(telefonos);

        /*  Correos
            Sí, para correos es el mismo proceso: compruebas si el string no está vacío,
            lo separas por ;, recorres cada trozo y construyes objetos Correo o como se 
            llame tu entidad. La idea es exactamente la misma que con teléfonos. */
            
        Set<Correo> correos = new HashSet<>();    

        if (!dircorreos.isEmpty()&& !dircorreos.isBlank()) {
        String[] arrayCorreos = dircorreos.split(";");
        List<String> listadoCorreos = Arrays.asList(arrayCorreos);

        listadoCorreos.forEach(correo -> {
            correos.add(Correo.builder()
                    .email(correo.trim())
                    .empleado(empleado)
                    .build());
        });
    }

        empleado.setCorreos(correos);

         /**Se recibe un objeto Empleado con los datos del formulario, se envia a la
         * capa de servicios para que lo guare en la DB
         */
        empleadoService.saveEmpleado(empleado);

        return "redirect:/empleados/listar";
    }


}
