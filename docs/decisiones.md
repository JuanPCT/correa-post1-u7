# Registro de decisiones · Unidad 7

1. **Cálculo en el modelo.** La tarifa diaria y el tope son invariantes de una multa; concentrarlos en `Multa.calcularMonto` reduce el riesgo de que cada controlador aplique una fórmula distinta.
2. **Límite en SQL.** El caso consulta cinco pendientes con `countByEstudianteIdAndEstado`; cargar el historial para contarlo aumentaría memoria y latencia conforme crece.
3. **Proveedor al iniciar.** La propiedad `biblioteca.pasarela` selecciona el adaptador. No se usa un `Map` por operación porque el proveedor no depende de cada estudiante ni de cada multa.
4. **Contrato de salida neutral.** `ResultadoPago` mantiene aprobación, referencia y mensaje en la aplicación; ningún DTO depende de clases de PagosUDES o Wompi.

Las capas son suficientes para catálogo y reglas locales. El puerto/adaptadores se introduce únicamente donde hay variación externa real, aceptando unas clases adicionales para que el caso de uso y el dominio no dependan de un proveedor.
