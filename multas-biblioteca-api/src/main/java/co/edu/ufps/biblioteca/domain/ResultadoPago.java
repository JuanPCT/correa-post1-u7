package co.edu.ufps.biblioteca.domain;

public record ResultadoPago(String proveedor, boolean exitoso, String referenciaExterna, String mensaje) { }
