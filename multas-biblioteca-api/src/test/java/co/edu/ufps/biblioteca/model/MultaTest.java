package co.edu.ufps.biblioteca.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class MultaTest {
    @Test void calculaYAplicaTopeDeLaMulta() {
        assertThat(Multa.calcularMonto(4)).isEqualByComparingTo(new BigDecimal("2000.00"));
        assertThat(Multa.calcularMonto(120)).isEqualByComparingTo(new BigDecimal("15000.00"));
    }
    @Test void rechazaRetrasoFueraDelRango() {
        assertThatThrownBy(() -> Multa.crear("E1", "L1", 0)).isInstanceOf(IllegalArgumentException.class);
    }
}
