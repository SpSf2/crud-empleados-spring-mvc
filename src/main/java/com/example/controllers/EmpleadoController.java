package com.example.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.services.CorreoService;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;
import com.example.services.TelefonoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
@RequestMapping("/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private static final Logger LOG = Logger.getLogger("EmpleadoController");
    
    private final EmpleadoService empleadoService;
    private final DepartamentoService departamentoService;
    private final CorreoService correoService;
    private final TelefonoService telefonoService;

    @GetMapping("/listar")
    public String listarEmpleados(Model model) {

        model.addAttribute("empleados", empleadoService.getAllEmpleados());

        return "listadoEmpleados";
    }

    //metodo para mostrar el formulario de creación de empleados
    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model, @ModelAttribute Empleado empleado) {

        //Se necesitan los Departamentos desde la capa de Servicios y lo inyectamos arriba
        model.addAttribute("departamentos", departamentoService.getAllDepartamentos());

        /*Se necesita enviar un Objeto Empleado vacio, para que se vinculen sus propiedades con cada control
        (elemento, input, select, etc) del formulario */

        // El codigo siguiente se comenta porque el objeto se pasa como atributo 
        // al modelo a traves de la anotacion @ModelAttribute que se recibe como un 
        // parametro del metodo
        // model.addAttribute("empleado", new Empleado());

        return "formularioAltaModificacion";
    }

    /*Metodo para recibir los datos del formulario de Alta de Empleado*/
    @PostMapping("/persistir")     
    public String procesarFormuarioaltaModificacion(@Valid 
        @ModelAttribute Empleado empleado, 
        BindingResult result,
        @RequestParam String numerosTelefono,
        @RequestParam String dircorreos,
        Model model,
        @RequestParam(required = false) MultipartFile file) {
        
            //Comprobar si hay errores en la información procedente del formulario
        if (result.hasErrors()) {
            
            model.addAttribute("departamentos", 
                                departamentoService.getAllDepartamentos());

            return "formularioAltaModificacion";
        }
        
            /*Preguntar si han enviado foto del Empleado y si es asi, guardar el nombre
            de la foto en la propiedad, atributo o variable donde se guarde la foto
            y guardar el contengido de la foto como un archivo en el sistema de archivos
            (files system) del servidor*/

            if (file != null && !file.isEmpty()) {
                Path rutaRelativa = Paths.get("src/main/resources/static/imagenes");
                String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + file.getOriginalFilename());

                try {
                    byte[] bytesFotoRecibida = file.getBytes();
                    Files.write(rutaCompleta, bytesFotoRecibida);
                    empleado.setFoto(file.getOriginalFilename());
                } catch (IOException e) { 
                    e.printStackTrace();
                }
            }


        LOG.info("Objeto Empleado Recibido");
        LOG.info(empleado.toString());
        LOG.info("Numeros de Telefono recibidos: " + numerosTelefono);
        LOG.info("Direcciones de Correo recibidas: " + dircorreos);

        //Hay que procesar los datos de los telefonos y correos que vienen en un String
		/*separados por comas y convertirlos en listas de objetos Telefono y Correo para
		 * luego agregarlos al objeto Empleado antes de persistirlo en la DB */
		
		//Set<Telefono> telefonos = new HashSet<Telefono>();
		
		if(!numerosTelefono.isEmpty()&& !numerosTelefono.isBlank() ) {
			
			String[] arrayNumerosTelefono = numerosTelefono.split(";");
			List<String> listadoNumeros = Arrays.asList(arrayNumerosTelefono);
                
        listadoNumeros.forEach(numero -> {
            empleado.getTelefonos().add(Telefono.builder().numero(numero).empleado(empleado).build());
		    });
        }
			//empleado.setTelefonos(telefonos);

        /*  Correos
            Sí, para correos es el mismo proceso: compruebas si el string no está vacío,
            lo separas por ;, recorres cada trozo y construyes objetos Correo o como se 
            llame tu entidad. La idea es exactamente la misma que con teléfonos. */
            
        // Set<Correo> correos = new HashSet<>();    

        if (!dircorreos.isEmpty()&& !dircorreos.isBlank()) {
        String[] arrayCorreos = dircorreos.split(";");
        List<String> listadoCorreos = Arrays.asList(arrayCorreos);

        listadoCorreos.forEach(correo -> {
            empleado.getCorreos().add(Correo.builder()
                    .email(correo.trim())
                    .empleado(empleado)
                    .build());
        });
    }

        /**
        * Antes de persistir el empleado, hay que eliminar los telefonos y los correos que tenga
        */
        if (telefonoService.existsByEmpleado(empleado))
        telefonoService.deleteByEmpleado(empleado);

        if (correoService.existsByEmpleado(empleado))
        correoService.deleteByEmpleado(empleado);

         /**Se recibe un objeto Empleado con los datos del formulario, se envia a la
         * capa de servicios para que lo guare en la DB
         */
        empleadoService.saveEmpleado(empleado);

        return "redirect:/empleados/listar";
    }


    // Metodo que muestra los detalles de un empleado cuyo id se recibe como parámetro:

    @GetMapping("/details/{id}")
    public String mostrarDetalles(Model model, @PathVariable(name = "id", required = true) int empleado_id) {

        //Recuperar el empleado cuyo id se recibe como parámetro:
        model.addAttribute("empleado", empleadoService.getEmpleadoById(empleado_id));
        return "details";
    }

    // Metodo para actualizar un empleado:
    /*Este metodo muestra en el formularioAltaModificación  la información del empleado a actualizar */
        @GetMapping(path="/update/{id}")
        public String updateEmpleado(Model model, 
                    @PathVariable(name = "id", required = true) int idEmpleado) {

            Empleado empleado = empleadoService.getEmpleadoById(idEmpleado);
            model.addAttribute("empleado", empleado);
                      
            //Se necesitan enviar los Departamentos para que salgan en la vista del formulario
            
            model.addAttribute("departamentos", departamentoService.getAllDepartamentos());

            /*Procesando los telefonos y correos porque no se debe hacer en la vista  */
            Set<Telefono> telefonos = empleado.getTelefonos(); 

            if(telefonos.size() > 0) {

                String numerosTelefono = telefonos.stream()
                        .map(telefono -> telefono.getNumero())
                        .collect(Collectors.joining(";"));

                model.addAttribute("numerosTelefono", numerosTelefono);
            }

            Set<Correo> correos = empleado.getCorreos();

            if(correos.size() > 0) {
                String dircorreos = correos.stream()
                        .map(correo -> correo.getEmail())
                        .collect(Collectors.joining(";"));

                model.addAttribute("dircorreos", dircorreos);
            }
            
            
            return "formularioAltaModificacion";
        }






}
