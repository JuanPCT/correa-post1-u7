package co.edu.ufps.biblioteca.controller;

import co.edu.ufps.biblioteca.service.MultaService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/multas")
public class MultaController {
    private final MultaService service;
    public MultaController(MultaService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<MultaResponse> crear(@jakarta.validation.Valid @RequestBody CrearMultaRequest request) {
        MultaResponse response = service.crear(request);
        return ResponseEntity.created(URI.create("/api/multas/" + response.id())).body(response);
    }

    @GetMapping
    public List<MultaResponse> listar() { return service.listar(); }

    @GetMapping("/estudiante/{estudianteId}")
    public List<MultaResponse> listarPorEstudiante(@PathVariable String estudianteId) { return service.listarPorEstudiante(estudianteId); }

    @GetMapping("/{id}")
    public MultaResponse consultar(@PathVariable Long id) { return service.consultar(id); }

    @PatchMapping("/{id}/pagar")
    public MultaResponse pagarVentanilla(@PathVariable Long id) { return service.pagarEnVentanilla(id); }

    @PostMapping("/{id}/pagar-en-linea")
    public PagoResponse pagarEnLinea(@PathVariable Long id) {
        return service.pagarEnLinea(id);
    }
}
