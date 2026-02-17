package com.exercise.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import com.exercise.model.Sorteo;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SorteoControllerPanacheTest {

    @Test
    public void testHealthEndpoint() {
        given()
                .when().get("/sorteos/health")
                .then()
                .statusCode(200)
                .body(containsString("Servicio Sorteos funcionando"));
    }

    @Test
    public void testObtenerSorteoExistente() {
        // Asumiendo que tienes datos de test en la BD
        given()
                .when().get("/sorteos/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void testObtenerSorteoNoExistente() {
        given()
                .when().get("/sorteos/9999")
                .then()
                .statusCode(404)
                .body("error", containsString("no encontrado"));
    }

    @Test
    public void testObtenerTodos() {
        given()
                .when().get("/sorteos/todos")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testObtenerRecientes() {
        given()
                .when().get("/sorteos/recientes?limite=5")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testObtenerRecientesConValorPorDefecto() {
        given()
                .when().get("/sorteos/recientes")
                .then()
                .statusCode(200);
    }

    @Test
    public void testObtenerPorRango() {
        given()
                .when().get("/sorteos/rango?desde=1&hasta=10")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testObtenerPorRangoSinParametros() {
        given()
                .when().get("/sorteos/rango")
                .then()
                .statusCode(400)
                .body("error", containsString("Parámetros 'desde' y 'hasta' son requeridos"));
    }

    @Test
    public void testEstadisticas() {
        given()
                .when().get("/sorteos/estadisticas")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testProbabilidadSorteo() {
        given()
                .when().get("/sorteos/probabilidad")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}
