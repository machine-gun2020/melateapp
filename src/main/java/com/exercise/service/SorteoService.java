package com.exercise.service;

import com.exercise.model.Sorteo;
import com.exercise.repository.SorteoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<Sorteo> getAllSorteo() {
        List<Sorteo> sontodos;
        sontodos = sorteoRepository.findAllOrderedByFechaDesc();
        return sontodos;
    }

    public Map<Integer, Double> calcularProbabilidad(List<Sorteo> sorteos, int topN) {
        if (sorteos.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Integer, Long> frecuencia = sorteos.stream()
                .flatMap(sorteo -> Stream.of(sorteo.getN1(), sorteo.getN2(), sorteo.getN3(),
                        sorteo.getN4(), sorteo.getN5(), sorteo.getN6(),
                        sorteo.getComodin()))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Calcular la probabilidad
        int totalSorteos = sorteos.size();
        Map<Integer, Double> probabilidades = frecuencia.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (double) entry.getValue() / (totalSorteos * 7),
                        (e1, e2) -> e1, LinkedHashMap::new
                ));

        // Ordenar por probabilidad descendente y limitar a los `topN` valores más altos
        return probabilidades.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(topN) // ← Filtrar solo los `N` más probables
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new
                ));
    }
}
