# Registro de decisiones · Unidad 7

1. **Cálculo en `Multa`.** El modelo aplica $500 COP por día y limita el total a $15.000 COP. El invariante queda junto a la entidad y evita fórmulas distintas en controladores; cambiar la tarifa implica tocar el dominio.
2. **Límite con consulta SQL.** `countByEstudianteIdAndEstado` cuenta multas pendientes y limita a tres. Evita cargar el historial completo, a cambio de un método derivado específico en Spring Data.
3. **Proveedor seleccionado al iniciar.** `@ConditionalOnProperty` elige `PagosUDES` o `Wompi` desde `app.pagos.proveedor`. Es sencillo cuando la selección depende del despliegue; un registro dinámico sería mejor si cada transacción eligiera su proveedor.
4. **Resultado neutral del puerto.** `ResultadoPago` reúne proveedor, aprobación, referencia y mensaje. El servicio no depende de DTOs externos, aunque cada adaptador debe traducir el resultado de su proveedor.

Se conservan capas para las reglas y persistencia locales. El puerto y los adaptadores agregan clases y configuración, pero permiten reemplazar la pasarela sin acoplar `MultaService` a una marca. Ambas integraciones son simuladas y generan referencias ficticias.
