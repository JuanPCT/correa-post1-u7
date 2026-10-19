package co.edu.ufps.biblioteca.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CrearMultaRequest(@NotBlank String estudianteId, @NotBlank String concepto, @Min(1) int diasAtraso) { }
