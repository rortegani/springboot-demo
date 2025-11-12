package com.campuslands.springbootdemo.service;

import com.campuslands.springbootdemo.entity.Categoria;
import com.campuslands.springbootdemo.repository.ICategoriaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Servicio de negocio para gestionar {@link Categoria}.
 * <p> * Expone operaciones CRUD y búsquedas por nombre y código. 
 * @since 1.0.0
 */
@Service
public class CategoriaService {

    private final ICategoriaRepository categoriaRepository;

    /**
     * Crea una nueva instancia del servicio.     
     * @param categoriaRepository repositorio de categorías
     */
    public CategoriaService(ICategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Crea una categoría y asigna la fecha de creación actual.    
     * @param categoria datos de la categoría
     * @return categoría persistida
     */
    public Categoria crear(Categoria categoria) {
        categoria.setFechaCreacion(LocalDateTime.now());

        return categoriaRepository.save(categoria);
    }

    /**
     * Obtiene una categoría por su identificador.    
     * @param id identificador de la categoría
     * @return la categoría si existe, o {@code null} en caso contrario
     */
    public Categoria obtener(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    /**
     * Lista todas las categorías.     
     * @return lista de categorías
     */
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    /**
     * Actualiza los campos editables de una categoría existente.     
     * @param id identificador de la categoría
     * @param categoria datos a actualizar
     * @return categoría actualizada o {@code null} si no existe
     */
    public Categoria actualizar(Long id, Categoria categoria) {
        var categoriaDB = categoriaRepository.findById(id).orElse(null);

        if (categoriaDB == null) {
            return null;
        }

        categoriaDB.setNombre(categoria.getNombre());
        categoriaDB.setDescripcion(categoria.getDescripcion());
        categoriaDB.setCodigo(categoria.getCodigo());
        categoriaDB.setDescuento(categoria.getDescuento());

        return categoriaRepository.save(categoriaDB);
    }

    /**
     * Elimina una categoría por su identificador.     
     * @param id identificador de la categoría
     */
    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }

    /**
     * Busca categorías por coincidencia en el nombre.     
     * @param nombre fragmento a buscar
     * @return lista de coincidencias
     */
    public List<Categoria> buscarPorNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Busca categorías por código exacto.     
     * @param codigo código de la categoría
     * @return lista de coincidencias
     */
    public List<Categoria> buscarPorCodigo(Integer codigo) {
        return categoriaRepository.findByCodigo(codigo);
    }

}
