package co.edu.ufps.biblioteca.controller;

import java.math.BigDecimal;

public record PagoResponse(MultaResponse multa, boolean exitoso, String proveedor,
                           String referenciaExterna, String mensaje) { }
