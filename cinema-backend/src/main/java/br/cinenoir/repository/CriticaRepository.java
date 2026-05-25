package br.cinenoir.repository;
import br.cinenoir.model.Critica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CriticaRepository extends JpaRepository<Critica, Long> {
    List<Critica> findByFilmeId(Long filmeId);
    List<Critica> findByNome(String nome);
}
