package co.edu.ufps.biblioteca.infrastructure.pago;

import co.edu.ufps.biblioteca.domain.port.PasarelaPagoPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasarelaConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "app.pagos", name = "proveedor", havingValue = "pagosudes", matchIfMissing = true)
    PasarelaPagoPort pagosUdes() { return new PagosUdesAdapter(); }

    @Bean
    @ConditionalOnProperty(prefix = "app.pagos", name = "proveedor", havingValue = "wompi")
    PasarelaPagoPort wompi() { return new WompiAdapter(); }
}
