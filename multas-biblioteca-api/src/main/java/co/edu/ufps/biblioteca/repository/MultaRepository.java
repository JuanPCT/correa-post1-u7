package co.edu.ufps.biblioteca.repository;

import co.edu.ufps.biblioteca.model.EstadoMulta;
import co.edu.ufps.biblioteca.model.Multa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultaRepository extends JpaRepository<Multa, Long> {
    long countByEstudianteIdAndEstado(String estudianteId, EstadoMulta estado);
    List<Multa> findByEstudianteId(String estudianteId);
}
