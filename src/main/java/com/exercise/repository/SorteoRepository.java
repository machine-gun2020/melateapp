package com.exercise.repository;

import com.exercise.model.Sorteo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SorteoRepository implements PanacheRepository<Sorteo> {

    // Consultar por ID
    public Optional<Sorteo> buscarPorId(Integer id) {
        return find("id", id).firstResultOptional();
    }

    // Consultar todos ordenados por ID
    public List<Sorteo> buscarTodos() {
        return list("ORDER BY id");
    }

    // Consultar por rango de IDs
    public List<Sorteo> buscarPorRango(Integer desde, Integer hasta) {
        return list("id BETWEEN ?1 AND ?2 ORDER BY id", desde, hasta);
    }

    // Consultar los más recientes
    public List<Sorteo> buscarRecientes(int limite) {
        return find("ORDER BY fecha DESC, id DESC")
                .page(0, limite)
                .list();
    }

    // Contar total de registros
    public Long contarSorteos() {
        return count();
    }
}
