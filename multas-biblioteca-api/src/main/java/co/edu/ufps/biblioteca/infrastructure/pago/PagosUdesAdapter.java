package co.edu.ufps.biblioteca.infrastructure.pago;

import co.edu.ufps.biblioteca.model.Multa;
import co.edu.ufps.biblioteca.domain.ResultadoPago;
import co.edu.ufps.biblioteca.domain.port.PasarelaPagoPort;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

public class PagosUdesAdapter implements PasarelaPagoPort {
    @Value("${app.pagos.simulacion.fallar:false}") private boolean simularRechazo;
    @Override public ResultadoPago procesar(Multa multa) {
        if (simularRechazo || multa.getConcepto().contains("SIMULAR_RECHAZO"))
            return new ResultadoPago("PAGOSUDES", false, null, "Transacción rechazada por PagosUDES simulada");
        return new ResultadoPago("PAGOSUDES", true, "UDES-" + UUID.randomUUID(), "Pago aprobado por PagosUDES simulada");
    }
}
