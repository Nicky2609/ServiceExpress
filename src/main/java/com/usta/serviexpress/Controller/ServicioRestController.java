package com.usta.serviexpress.Controller;

import com.usta.serviexpress.Entity.ServicioEntity;
import com.usta.serviexpress.Entity.UsuarioEntity;
import com.usta.serviexpress.Repository.ServicioRepository;
import com.usta.serviexpress.Service.ServicioService;
import com.usta.serviexpress.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioRestController {

    @Autowired
    private ServicioService servicioService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping
    public List<ServicioEntity> listar() {
        return servicioService.findAll();
    }

    @GetMapping("/{id}")
    public ServicioEntity buscarPorId(@PathVariable Long id) {
        return servicioService.findById(id);
    }
    @PostMapping
    public ResponseEntity<ServicioEntity> guardar(@RequestBody ServicioEntity servicio) {
        try {
            // 🔹 Si el JSON trae un idProveedor o idCliente, los buscamos en la base de datos
            if (servicio.getProveedor() != null && servicio.getProveedor().getIdUsuario() != null) {
                UsuarioEntity proveedor = usuarioService.findById(servicio.getProveedor().getIdUsuario());
                servicio.setProveedor(proveedor);
            }

            if (servicio.getCliente() != null && servicio.getCliente().getIdUsuario() != null) {
                UsuarioEntity cliente = usuarioService.findById(servicio.getCliente().getIdUsuario());
                servicio.setCliente(cliente);
            }

            // 🔹 Guardamos el servicio ya con los vínculos completos
            servicioService.save(servicio);
            System.out.println("Proveedor recibido: " + (servicio.getProveedor() != null ? servicio.getProveedor().getIdUsuario() : "NULO"));

            return ResponseEntity.ok(servicio);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        servicioService.deleteById(id);
    }

}
