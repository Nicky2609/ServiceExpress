package com.usta.serviexpress.Service;

import com.usta.serviexpress.Entity.ServicioEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ServicioApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public ServicioEntity obtenerServicioDesdeApi(String tipoServicio) {
        // URL de ejemplo — cámbiala por la real de tu API
        String url = "https://api.ejemplo.com/servicios/" + tipoServicio;

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> datos = response.getBody();

        if (datos == null) {
            throw new RuntimeException("No se obtuvo respuesta de la API");
        }

        // Crear un ServicioEntity con la información recibida
        ServicioEntity servicio = new ServicioEntity();
        servicio.setNombre((String) datos.get("nombre"));
        servicio.setDescripcion((String) datos.get("descripcion"));
        servicio.setPrecio(new BigDecimal(datos.get("precio").toString()));
        servicio.setEstado(ServicioEntity.EstadoServicio.DISPONIBLE);

        return servicio;
    }
}
