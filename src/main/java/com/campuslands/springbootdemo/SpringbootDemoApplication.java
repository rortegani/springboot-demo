package com.campuslands.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal de Spring Boot para el proyecto springboot-demo.
 * <p> * Arranca el contexto de Spring y expone los controladores REST.
 * @since 1.0.0
 */
@SpringBootApplication
public class SpringbootDemoApplication {

    /**
     * Punto de entrada de la aplicación.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }

}
