package com.campuslands.springbootdemo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * Entidad que representa un producto del catálogo.
 * <p> * Cada producto pertenece a una {@link Categoria}.
 * @since 1.0.0
 */
@Schema(description = "Entidad que representa un producto comercializado dentro del catálogo")
@Entity
@Table(name = "productos")
public class Producto {

    /** Identificador primario autogenerado. */
    @Schema(description = "Identificador único del producto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del producto. Obligatorio. */
    @Schema(description = "Nombre del producto (obligatorio)", example = "Audífonos Bluetooth")
    @Column(nullable = false)
    private String nombre;

    /** Precio del producto. */
    @Schema(description = "Precio unitario del producto en moneda local", example = "249.9")
    private Double precio;

    /** Cantidad disponible en inventario. */
    @Schema(description = "Cantidad disponible en inventario", example = "50")
    private Integer stock;

    /** Fecha y hora de registro del producto. */
    @Schema(description = "Fecha y hora de registro del producto en el sistema", format = "date-time", example = "2025-01-05T09:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime fechaRegistro;

    /** Categoría a la que pertenece el producto. No nula. */
    @Schema(description = "Categoría a la que pertenece este producto (relación muchos-a-uno)")
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    /** 
     * Devuelve el identificador único del producto.
     * @return id del producto
     */
    public Long getId() {
        return id;
    }

    /** 
     * Asigna el identificador único del producto.
     * @param id identificador a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * Devuelve el nombre del producto.
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /** 
     * Asigna el nombre del producto.
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** 
     * Devuelve el precio unitario del producto.
     * @return precio del producto
     */
    public Double getPrecio() {
        return precio;
    }

    /** 
     * Asigna el precio unitario del producto.
     * @param precio precio a asignar
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /** 
     * Devuelve la cantidad disponible en inventario.
     * @return unidades en stock
     */
    public Integer getStock() {
        return stock;
    }

    /** 
     * Asigna la cantidad disponible en inventario.
     * @param stock unidades a asignar
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /** 
     * Devuelve la fecha y hora en que se registró el producto.
     * @return fecha de registro
     */
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    /** 
     * Asigna la fecha y hora de registro del producto.
     * @param fechaRegistro fecha/hora de registro
     */
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /** 
     * Devuelve la categoría asociada al producto.
     * @return categoría asociada
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /** 
     * Asigna la categoría a la que pertenece el producto.
     * @param categoria categoría a asociar
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
