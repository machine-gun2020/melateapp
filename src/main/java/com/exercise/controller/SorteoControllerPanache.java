package com.exercise.controller;


import com.exercise.model.Sorteo;
import com.exercise.service.SorteoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.quarkus.agroal.runtime.AgroalConnectionConfigurer.log;

@Path("/sorteos")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
public class SorteoControllerPanache {
    private static final Logger log = LoggerFactory.getLogger(SorteoControllerPanache.class);
    @Inject
    SorteoService sorteoService;

    // Health check
    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public Response health() {
        log.info("Salida");
        return Response.ok("{\"status\": \"Servicio Sorteos funcionando\"}").build();
    }

    // Consultar por ID
    @GET
    @Path("/{id}")
    public Response obtenerSorteo(@PathParam("id") Integer id) {
        Optional<Sorteo> sorteo = sorteoService.obtenerSorteo(id);

        if (sorteo.isPresent()) {
            return Response.ok(sorteo.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sorteo con ID " + id + " no encontrado\"}")
                    .build();
        }
    }

    // Consultar todos
    @GET
    @Path("/todos")
    public Response obtenerTodos() {
        List<Sorteo> sorteos = sorteoService.obtenerTodos();
        return Response.ok(sorteos).build();
    }

    // Consultar recientes
    @GET
    @Path("/recientes")
    public Response obtenerRecientes(@QueryParam("limite") @DefaultValue("5") Integer limite) {
        List<Sorteo> sorteos = sorteoService.obtenerRecientes(limite);
        return Response.ok(sorteos).build();
    }

    // Consultar por rango
    @GET
    @Path("/rango")
    public Response obtenerPorRango(
            @QueryParam("desde") Integer desde,
            @QueryParam("hasta") Integer hasta) {

        if (desde == null || hasta == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Parámetros 'desde' y 'hasta' son requeridos\"}")
                    .build();
        }

        List<Sorteo> sorteos = sorteoService.obtenerPorRango(desde, hasta);
        return Response.ok(sorteos).build();
    }

    // Estadísticas
    @GET
    @Path("/estadisticas")
    public Response estadisticas() {
        String stats = sorteoService.obtenerEstadisticas();
        return Response.ok("{\"message\": \"" + stats + "\"}").build();
    }

    @GET
    @Path("/probabilidad")
    public Map<Integer, Double> getProbabilidadSorteo() {
        List<Sorteo> cuales = sorteoService.getAllSorteo();
        log.info("Salida, probabilidad: {}", cuales.size());

        return sorteoService.calcularProbabilidad(cuales,7);
    }
}
