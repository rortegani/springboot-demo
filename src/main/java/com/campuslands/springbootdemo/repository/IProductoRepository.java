package com.campuslands.springbootdemo.repository;

import com.campuslands.springbootdemo.entity.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad {@link Producto}.
 * <p> * Incluye consultas derivadas para búsqueda por nombre y filtro por precio.
 * @since 1.0.0
 */
public interface IProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Busca productos cuyo nombre contenga el texto indicado,
     * ignorando mayúsculas/minúsculas.     
     * @param nombre fragmento del nombre a buscar
     * @return lista de productos coincidentes
     */
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Obtiene productos con precio estrictamente mayor al valor dado.    
     * @param precio umbral de precio
     * @return lista de productos con precio mayor
     */
    List<Producto> findByPrecioGreaterThan(Double precio);

}
