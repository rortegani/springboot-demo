package com.campuslands.springbootdemo.controller;

import com.campuslands.springbootdemo.entity.Categoria;
import com.campuslands.springbootdemo.service.CategoriaService;
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
 * Controlador REST para la gestión de {@link Categoria}.
 * <p> * Expone operaciones CRUD y consultas por nombre y código. 
 * @since 1.0.0
 */
@Tag(name = "Categorías", description = "CRUD y búsquedas de categorías")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Crea una nueva instancia del controlador.     
     * @param categoriaService servicio de categorías
     */
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Crea una categoría.     
     * @param categoria cuerpo de la solicitud con los datos de la categoría
     * @return {@code 200 OK} con la categoría creada
     */
    @Operation(summary = "Crear categoría",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Categoria.class),
                            examples = @ExampleObject(value = """
                                                              {"nombre":"Electrónica", "descripcion":"Dispositivos", "codigo":10,"descuento":5.5}
                                                              """))),
            responses = {@ApiResponse(responseCode = "200", description = "Creada", content = @Content(schema = @Schema(implementation = Categoria.class)))}
    )
    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.crear(categoria));
    }

    /**
     * Obtiene una categoría por su identificador.     
     * @param id identificador de la categoría
     * @return {@code 200 OK} si existe o {@code 404 Not Found} si no existe
     */
    @Operation(summary = "Obtener categoría por id",
            parameters = { @Parameter(name = "id", description = "Identificador de la categoría", required = true, example = "1")},
            responses = {
                @ApiResponse(responseCode = "200", description = "OK", 
                        content = @Content(mediaType = "application/json", 
                                schema = @Schema(implementation = Categoria.class))),
                @ApiResponse(responseCode = "404", description = "No encontrada")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtener(@PathVariable Long id) {
        var categoria = categoriaService.obtener(id);
        return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

    /**
     * Lista todas las categorías.     
     * @return lista de categorías
     */
    @Operation(summary = "Listar categorías",
            responses = {
                @ApiResponse(responseCode = "200", description = "OK",
                        content = @Content(mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = Categoria.class))))
        }
    )
    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.listar();
    }

    /**
     * Actualiza una categoría existente.     
     * @param id identificador de la categoría
     * @param categoria datos a actualizar
     * @return {@code 200 OK} con la categoría actualizada o {@code 404 Not Found}
     */
    @Operation(summary = "Actualizar categoría",
        parameters = {@Parameter(name = "id", description = "Identificador de la categoría", required = true, example = "1")},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true, 
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Categoria.class),
                examples = @ExampleObject(value = """
                                                  {"nombre":"Electro","descripcion":"Actualizada","codigo":20,"descuento":7.0}
                                                  """)
                )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Actualizada",
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "404", description = "No encontrada")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        var categoriaActualizada = categoriaService.actualizar(id, categoria);
        return categoriaActualizada != null ? ResponseEntity.ok(categoriaActualizada) : ResponseEntity.notFound().build();
    }

    /**
     * Elimina una categoría por su identificador.     
     * @param id identificador de la categoría
     * @return {@code 204 No Content}
     */
    @Operation(
            summary = "Eliminar categoría",
            parameters = {
                @Parameter(name = "id", description = "Identificador de la categoría", required = true, example = "1")
            },
            responses = {
                @ApiResponse(responseCode = "204", description = "Eliminada sin contenido")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca categorías por nombre.     
     * @param nombre texto a buscar (coincidencia parcial, sin distinguir mayúsculas)
     * @return lista de coincidencias
     */
    @Operation(
            summary = "Buscar categorías por nombre",
            parameters = {
                @Parameter(name = "nombre", description = "Texto a buscar (contiene, case-insensitive)", required = true, example = "electro")
            },
            responses = {
                @ApiResponse(responseCode = "200", description = "OK",
                        content = @Content(mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = Categoria.class))))
            }
    )
    @GetMapping("/buscar/nombre")
    public List<Categoria> buscarPorNombre(@RequestParam String nombre) {
        return categoriaService.buscarPorNombre(nombre);
    }

    /**
     * Busca categorías por código exacto.     
     * @param codigo código de categoría
     * @return lista de coincidencias
     */
    @Operation(
            summary = "Buscar categorías por código",
            parameters = {
                @Parameter(name = "codigo", description = "Código exacto de la categoría", required = true, example = "10")
            },
            responses = {
                @ApiResponse(responseCode = "200", description = "OK",
                        content = @Content(mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = Categoria.class))))
            }
    )
    @GetMapping("/buscar/codigo")
    public List<Categoria> buscarPorCodigo(@RequestParam Integer codigo) {
        return categoriaService.buscarPorCodigo(codigo);
    }
}
