package com.campuslands.springbootdemo.controller;

import com.campuslands.springbootdemo.entity.Producto;
import com.campuslands.springbootdemo.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para la gestión de {@link Producto}.
 * <p> * Expone operaciones CRUD, búsqueda por nombre y filtro por precio. 
 * @since 1.0.0
 */
@Tag(name = "Productos", description = "CRUD y búsquedas de productos")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    
    /**
     * Crea una nueva instancia del controlador.     
     * @param productoService servicio de productos
     */
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    
    /**
     * Crea un producto dentro de una categoría existente.     
     * @param categoriaId identificador de la categoría a asociar
     * @param producto cuerpo de la solicitud con los datos del producto
     * @return {@code 200 OK} con el producto creado o {@code 400 Bad Request} si la categoría no existe
     */
    @Operation(
            summary = "Crear producto",
            description = "Requiere el parámetro de consulta `categoriaId` existente.",
            parameters = {
                @Parameter(name = "categoriaId", description = "Id de la categoría destino", required = true, example = "1")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class),
                            examples = @ExampleObject(value = """
                    {"nombre":"Audífonos","precio":249.9,"stock":50}
                """)
                    )
            ),
            responses = {
                @ApiResponse(responseCode = "200", description = "Creado",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Producto.class))),
                @ApiResponse(responseCode = "400", description = "categoriaId inválido")
            }
    )
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestParam Long categoriaId, @RequestBody Producto producto) {
        var creado = productoService.crear(producto, categoriaId);
        return creado != null ? ResponseEntity.ok(creado) : ResponseEntity.badRequest().build();
    }

    /**
     * Obtiene un producto por su identificador.     
     * @param id identificador del producto
     * @return {@code 200 OK} si existe o {@code 404 Not Found} si no existe
     */
    @Operation(
            summary = "Obtener producto por id",
            parameters = {
                @Parameter(name = "id", description = "Identificador del producto", required = true, example = "1")
            },
            responses = {
                @ApiResponse(responseCode = "200", description = "OK",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Producto.class))),
                @ApiResponse(responseCode = "404", description = "No encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        var p = productoService.obtener(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    /**
     * Lista todos los productos.    
     * @return lista de productos
     */
    @Operation(
            summary = "Listar productos",
            responses = {
                @ApiResponse(responseCode = "200", description = "OK",
                        content = @Content(mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = Producto.class))))
            }
    )
    @GetMapping
    public List<Producto> listar() {
        return productoService.listar();
    }

    /**
     * Actualiza un producto existente y, opcionalmente, su categoría.    
     * @param id identificador del producto
     * @param categoriaId identificador de la nueva categoría (opcional)
     * @param producto datos a actualizar
     * @return {@code 200 OK} con el producto actualizado o {@code 404 Not Found}
     */
    @Operation(
            summary = "Actualizar producto",
            description = "Puede cambiar opcionalmente la categoría con `categoriaId`.",
            parameters = {
                @Parameter(name = "id", description = "Identificador del producto", required = true, example = "1"),
                @Parameter(name = "categoriaId", description = "Nueva categoría (opcional)", required = false, example = "2")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class),
                            examples = @ExampleObject(value = """
                    {"nombre":"Audífonos Pro","precio":299.9,"stock":40}
                """)
                    )
            ),
            responses = {
                @ApiResponse(responseCode = "200", description = "Actualizado",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Producto.class))),
                @ApiResponse(responseCode = "404", description = "No encontrado")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestParam(required = false) Long categoriaId, @RequestBody Producto producto) {
        var productoActualizado = productoService.actualizar(id, producto, categoriaId);
        return productoActualizado != null ? ResponseEntity.ok(productoActualizado) : ResponseEntity.notFound().build();
    }

    /**
     * Elimina un producto por su identificador.     
     * @param id identificador del producto
     * @return {@code 204 No Content}
     */
    @Operation(
            summary = "Eliminar producto",
            parameters = {
                @Parameter(name = "id", description = "Identificador del producto", required = true, example = "1")
            },
            responses = {
                @ApiResponse(responseCode = "204", description = "Eliminado sin contenido")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca productos por coincidencia en el nombre.    
     * @param nombre texto a buscar (coincidencia parcial, sin distinguir mayúsculas)
     * @return lista de coincidencias
     */
    @Operation(
            summary = "Buscar productos por nombre",
            parameters = {
                @Parameter(name = "nombre", description = "Texto a buscar (contiene, case-insensitive)", required = true, example = "udio")
            },
            responses = {
                @ApiResponse(responseCode = "200", description = "OK",
                        content = @Content(mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = Producto.class))))
            }
    )
    @GetMapping("/buscar/nombre")
    public List<Producto> buscarPorNombre(@RequestParam String nombre) {
        return productoService.buscarPorNombre(nombre);
    }

    /**
     * Obtiene productos con precio mayor al valor indicado.    
     * @param precio umbral de precio
     * @return lista de productos resultante
     */
    @Operation(
            summary = "Buscar productos con precio mayor a",
            parameters = {
                @Parameter(name = "precio", description = "Umbral de precio. Devuelve productos con precio > valor", required = true, example = "200.0")
            },
            responses = {
                @ApiResponse(responseCode = "200", description = "OK",
                        content = @Content(mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = Producto.class)))),
                @ApiResponse(responseCode = "400", description = "Parámetro inválido",
                        content = @Content) 
            }
    )
    @GetMapping("/buscar/precio")
    public List<Producto> buscarPorPrecioMayorA(@RequestParam Double precio) {
        return productoService.buscarPorPrecioMayorA(precio);
    }
}
