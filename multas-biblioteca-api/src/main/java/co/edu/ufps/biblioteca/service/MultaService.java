package co.edu.ufps.biblioteca.service;

import co.edu.ufps.biblioteca.controller.CrearMultaRequest;
import co.edu.ufps.biblioteca.controller.MultaResponse;
import co.edu.ufps.biblioteca.controller.PagoResponse;
import co.edu.ufps.biblioteca.domain.ResultadoPago;
import co.edu.ufps.biblioteca.domain.port.PasarelaPagoPort;
import co.edu.ufps.biblioteca.model.EstadoMulta;
import co.edu.ufps.biblioteca.model.LimiteMultasPendientesException;
import co.edu.ufps.biblioteca.model.Multa;
import co.edu.ufps.biblioteca.model.MultaNotFoundException;
import co.edu.ufps.biblioteca.repository.MultaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MultaService {
    private static final long MAX_MULTAS_PENDIENTES = 3;
    private final MultaRepository repository;
    private final PasarelaPagoPort pasarela;

    public MultaService(MultaRepository repository, PasarelaPagoPort pasarela) {
        this.repository = repository;
        this.pasarela = pasarela;
    }

    @Transactional
    public MultaResponse crear(CrearMultaRequest request) {
        if (repository.countByEstudianteIdAndEstado(request.estudianteId(), EstadoMulta.PENDIENTE) >= MAX_MULTAS_PENDIENTES) {
            throw new LimiteMultasPendientesException("El estudiante ya tiene tres multas pendientes");
        }
        return respuesta(repository.save(Multa.crear(request.estudianteId(), request.concepto(), request.diasAtraso())));
    }

    @Transactional(readOnly = true)
    public List<MultaResponse> listar() {
        return repository.findAll().stream().map(this::respuesta).toList();
    }

    @Transactional(readOnly = true)
    public List<MultaResponse> listarPorEstudiante(String estudianteId) {
        return repository.findByEstudianteId(estudianteId).stream().map(this::respuesta).toList();
    }

    @Transactional(readOnly = true)
    public MultaResponse consultar(Long id) { return respuesta(buscar(id)); }

    @Transactional
    public MultaResponse pagarEnVentanilla(Long id) {
        Multa multa = buscar(id);
        multa.marcarComoPagada("VENTANILLA");
        return respuesta(repository.save(multa));
    }

    @Transactional
    public PagoResponse pagarEnLinea(Long id) {
        Multa multa = buscar(id);
        ResultadoPago resultado = pasarela.procesar(multa);
        if (!resultado.exitoso()) throw new co.edu.ufps.biblioteca.domain.PagoRechazadoException(resultado.mensaje());
        multa.marcarComoPagada("EN_LINEA");
        repository.save(multa);
        return new PagoResponse(respuesta(multa), true, resultado.proveedor(), resultado.referenciaExterna(), resultado.mensaje());
    }

    private Multa buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new MultaNotFoundException("No existe la multa " + id));
    }

    private MultaResponse respuesta(Multa multa) {
        return new MultaResponse(multa.getId(), multa.getEstudianteId(), multa.getConcepto(), multa.getDiasAtraso(),
            multa.getMonto(), multa.getEstado(), multa.getFechaGeneracion(), multa.getFechaPago(), multa.getMetodoPago());
    }
}
