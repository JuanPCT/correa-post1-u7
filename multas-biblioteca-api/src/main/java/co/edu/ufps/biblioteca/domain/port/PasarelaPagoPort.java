package co.edu.ufps.biblioteca.domain.port;

import co.edu.ufps.biblioteca.domain.ResultadoPago;
import co.edu.ufps.biblioteca.model.Multa;

public interface PasarelaPagoPort {
    ResultadoPago procesar(Multa multa);
}
