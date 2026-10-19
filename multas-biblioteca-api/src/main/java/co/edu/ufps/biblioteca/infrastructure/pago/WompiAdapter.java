package co.edu.ufps.biblioteca.infrastructure.pago;

import co.edu.ufps.biblioteca.domain.ResultadoPago;
import co.edu.ufps.biblioteca.domain.port.PasarelaPagoPort;
import co.edu.ufps.biblioteca.model.Multa;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

public class WompiAdapter implements PasarelaPagoPort {
    @Value("${app.pagos.simulacion.fallar:false}") private boolean simularRechazo;
    @Override public ResultadoPago procesar(Multa multa) {
        if (simularRechazo || multa.getConcepto().contains("SIMULAR_RECHAZO"))
            return new ResultadoPago("WOMPI", false, null, "Transacción rechazada por Wompi simulada");
        return new ResultadoPago("WOMPI", true, "WOMPI-" + UUID.randomUUID(), "Pago aprobado por Wompi simulada");
    }
}
