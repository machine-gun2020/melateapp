package com.exercise.service;

import com.exercise.model.Sorteo;
import com.exercise.repository.SorteoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SorteoService {

    @Inject
    SorteoRepository sorteoRepository;

    // Consultar sorteo por ID
    public Optional<Sorteo> obtenerSorteo(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return sorteoRepository.buscarPorId(id);
    }

    // Consultar todos los sorteos
    public List<Sorteo> obtenerTodos() {
        return sorteoRepository.buscarTodos();
    }

    // Consultar sorteos recientes
    public List<Sorteo> obtenerRecientes(int limite) {
        return sorteoRepository.buscarRecientes(limite);
    }

    // Consultar por rango
    public List<Sorteo> obtenerPorRango(Integer desde, Integer hasta) {
        return sorteoRepository.buscarPorRango(desde, hasta);
    }

    // Obtener estadísticas
    public String obtenerEstadisticas() {
        Long total = sorteoRepository.contarSorteos();
        return String.format("Total de sorteos en sistema: %,d", total);
    }

    // Verificar existencia
    public boolean existeSorteo(Integer id) {
        return sorteoRepository.buscarPorId(id).isPresent();
    }
}
