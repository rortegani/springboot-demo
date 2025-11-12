package com.campuslands.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa una categoría del catálogo.
 * <p>* Relación uno-a-muchos con {@link Producto}.
 * @since 1.0.0
 */
@Schema(description = "Entidad que representa una categoría de productos")
@Entity
@Table(name = "categorias")
public class Categoria {

    /** Identificador primario autogenerado. */
    @Schema(description = "Identificador único de la categoría", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre de la categoría. Único y no nulo. */
    @Schema(description = "Nombre único y obligatorio de la categoría", example = "Electrónica")
    @Column(nullable = false, unique = true)
    private String nombre;

    /** Descripción opcional. */
    @Schema(description = "Descripción breve de la categoría", example = "Productos tecnológicos")
    private String descripcion;

    /** Código numérico de referencia. */
    @Schema(description = "Código interno de referencia", example = "10")
    private Integer codigo;

    /** Porcentaje de descuento asociado a la categoría. */
    @Schema(description = "Porcentaje de descuento aplicado", example = "5.5")
    private Double descuento;

    /** Fecha y hora de creación de la categoría. */
    @Schema(description = "Fecha y hora de creación", format = "date-time", example = "2025-01-01T10:15:30", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime fechaCreacion;

    /**
     * Productos asociados a la categoría.
     * Mapeo inverso. Ignorado en la serialización para evitar ciclos.
     */
    @Schema(description = "Productos asociados (solo lectura)", accessMode = Schema.AccessMode.READ_ONLY)
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Producto> productos;

    /** 
     * Devuelve el identificador único de la categoría.
     * @return id de la categoría 
     */
    public Long getId() {
        return id;
    }
    
    /** 
     * Asigna el identificador único de la categoría.
     * @param id identificador a asignar 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * Devuelve el nombre de la categoría.
     * @return nombre de la categoría 
     */
    public String getNombre() {
        return nombre;
    }

    /** 
     * Asigna el nombre de la categoría.
     * @param nombre nombre a asignar 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** 
     * Devuelve la descripción de la categoría.
     * @return descripción de la categoría 
     */
    public String getDescripcion() {
        return descripcion;
    }

    /** 
     * Asigna la descripción de la categoría.
     * @param descripcion descripción a asignar 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** 
     * Devuelve el código interno de la categoría.
     * @return código numérico 
     */
    public Integer getCodigo() {
        return codigo;
    }

    /** 
     * Asigna el código interno de la categoría.
     * @param codigo código a asignar 
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    /** 
     * Devuelve el porcentaje de descuento asociado.
     * @return descuento aplicado 
     */
    public Double getDescuento() {
        return descuento;
    }

    /** 
     * Asigna el porcentaje de descuento.
     * @param descuento porcentaje de descuento 
     */
    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    /** 
     * Devuelve la fecha y hora de creación de la categoría.
     * @return fecha de creación 
     */
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    /** 
     * Asigna la fecha y hora de creación de la categoría.
     * @param fechaCreacion fecha/hora de creación 
     */
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** 
     * Devuelve la lista de productos asociados a la categoría.
     * @return lista de productos asociados 
     */
    public List<Producto> getProductos() {
        return productos;
    }

    /** 
     * Asigna la lista de productos asociados a la categoría.
     * @param productos productos a asociar 
     */
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

}
