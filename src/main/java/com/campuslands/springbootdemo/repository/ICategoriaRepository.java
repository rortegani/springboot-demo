package com.campuslands.springbootdemo.repository;

import com.campuslands.springbootdemo.entity.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad {@link Categoria}.
 * <p> * Proporciona operaciones CRUD y consultas derivadas por nombre de método.
 * @since 1.0.0
 */
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    /**
     * Busca categorías cuyo nombre contenga el texto indicado,
     * ignorando mayúsculas/minúsculas.     
     * @param nombre fragmento del nombre a buscar
     * @return lista de categorías coincidentes
     */
    List<Categoria> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca categorías por un código exacto.    
     * @param codigo código de categoría
     * @return lista de categorías con ese código
     */
    List<Categoria> findByCodigo(Integer codigo);
}
