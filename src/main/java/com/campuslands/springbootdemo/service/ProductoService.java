package com.campuslands.springbootdemo.service;

import com.campuslands.springbootdemo.entity.Categoria;
import com.campuslands.springbootdemo.entity.Producto;
import com.campuslands.springbootdemo.repository.ICategoriaRepository;
import com.campuslands.springbootdemo.repository.IProductoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Servicio de negocio para gestionar {@link Producto}.
 * <p> * Controla la asociación con {@link Categoria} y las operaciones CRUD.
 * @since 1.0.0
 */
@Service
public class ProductoService {

    private final IProductoRepository productoRepository;

    private final ICategoriaRepository categoriaRepository;

    /**
     * Crea una nueva instancia del servicio.     
     * @param productoRepository repositorio de productos
     * @param categoriaRepository repositorio de categorías
     */
    public ProductoService(IProductoRepository productoRepository, ICategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Crea un producto asociado a una categoría existente.
     * También asigna la fecha de registro actual.     
     * @param producto datos del producto
     * @param categoriaId identificador de la categoría
     * @return producto creado o {@code null} si la categoría no existe
     */
    public Producto crear(Producto producto, Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);

        if (categoria == null) {
            return null;
        }

        producto.setCategoria(categoria);
        producto.setFechaRegistro(LocalDateTime.now());

        return productoRepository.save(producto);
    }

    /**
     * Obtiene un producto por su identificador.
     * @param id identificador del producto
     * @return el producto si existe, o {@code null} en caso contrario
     */
    public Producto obtener(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    /**
     * Lista todos los productos.
     * @return lista de productos
     */
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    /**
     * Actualiza los campos editables de un producto existente.
     * Puede reasignar la categoría si se proporciona un {@code categoriaId}.
     * @param id identificador del producto
     * @param producto datos a actualizar
     * @param categoriaId identificador de la nueva categoría (opcional)
     * @return producto actualizado o {@code null} si no existe
     */
    public Producto actualizar(Long id, Producto producto, Long categoriaId) {
        var productoDB = productoRepository.findById(id).orElse(null);

        if (productoDB == null) {
            return null;
        }

        productoDB.setNombre(producto.getNombre());
        productoDB.setPrecio(producto.getPrecio());
        productoDB.setStock(producto.getStock());

        if (categoriaId != null) {
            categoriaRepository.findById(categoriaId).ifPresent(productoDB::setCategoria);
        }

        return productoRepository.save(productoDB);
    }

    /**
     * Elimina un producto por su identificador.     
     * @param id identificador del producto
     */
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    /**
     * Busca productos por coincidencia en el nombre.     
     * @param nombre fragmento a buscar
     * @return lista de coincidencias
     */
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Obtiene productos con precio mayor al valor indicado.     
     * @param precio umbral de precio
     * @return lista de productos resultante
     */
    public List<Producto> buscarPorPrecioMayorA(Double precio) {
        return productoRepository.findByPrecioGreaterThan(precio);
    }
}
