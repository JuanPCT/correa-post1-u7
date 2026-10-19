package co.edu.ufps.biblioteca.controller;

import co.edu.ufps.biblioteca.model.EstadoMulta;
import java.math.BigDecimal;
import java.time.LocalDate;

public record MultaResponse(Long id, String estudianteId, String concepto, int diasAtraso,
                            BigDecimal monto, EstadoMulta estado, LocalDate fechaGeneracion,
                            LocalDate fechaPago, String metodoPago) { }
