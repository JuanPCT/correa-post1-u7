package co.edu.ufps.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "multas")
public class Multa {
    private static final BigDecimal VALOR_POR_DIA = new BigDecimal("500");
    private static final BigDecimal TOPE_MAXIMO = new BigDecimal("15000");

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 40)
    private String estudianteId;
    @Column(nullable = false, length = 180)
    private String concepto;
    @Column(nullable = false)
    private int diasAtraso;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 16)
    private EstadoMulta estado = EstadoMulta.PENDIENTE;
    @Column(nullable = false)
    private LocalDate fechaGeneracion = LocalDate.now();
    private LocalDate fechaPago;
    @Column(length = 30)
    private String metodoPago;

    protected Multa() { }

    public static Multa crear(String estudianteId, String concepto, int diasAtraso) {
        if (diasAtraso < 1) throw new IllegalArgumentException("Los días de atraso deben ser al menos 1");
        Multa multa = new Multa();
        multa.estudianteId = estudianteId;
        multa.concepto = concepto;
        multa.diasAtraso = diasAtraso;
        multa.monto = calcularMonto(diasAtraso);
        return multa;
    }

    public static BigDecimal calcularMonto(int diasAtraso) {
        if (diasAtraso < 1) throw new IllegalArgumentException("Los días de atraso deben ser al menos 1");
        return VALOR_POR_DIA.multiply(BigDecimal.valueOf(diasAtraso)).min(TOPE_MAXIMO);
    }

    public void marcarComoPagada(String metodo) {
        if (estado == EstadoMulta.PAGADA) throw new MultaYaPagadaException("La multa " + id + " ya fue pagada");
        estado = EstadoMulta.PAGADA;
        fechaPago = LocalDate.now();
        metodoPago = metodo;
    }

    public Long getId() { return id; }
    public String getEstudianteId() { return estudianteId; }
    public String getConcepto() { return concepto; }
    public int getDiasAtraso() { return diasAtraso; }
    public BigDecimal getMonto() { return monto; }
    public EstadoMulta getEstado() { return estado; }
    public LocalDate getFechaGeneracion() { return fechaGeneracion; }
    public LocalDate getFechaPago() { return fechaPago; }
    public String getMetodoPago() { return metodoPago; }
}
