package com.exercise.repository;

import com.exercise.model.Sorteo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SorteoRepository implements PanacheRepository<Sorteo> {
    @PersistenceContext
    EntityManager em = null;
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

    public List<Sorteo> findAllOrderedByFechaDesc() {
        return em.createQuery("SELECT s FROM Sorteo s ORDER BY s.fecha DESC", Sorteo.class)
                .setHint("org.hibernate.readOnly", true)      // No modifica datos
                .setHint("org.hibernate.fetchSize", 50)       // Recupera en lotes de 50
                .setHint("org.hibernate.cacheable", true)     // Usa caché de segundo nivel
                //.setHint("jakarta.persistence.query.retrieveMode", "USE")
                //.setHint("jakarta.persistence.query.storeMode", "USE")
                .getResultList();
    }
}
