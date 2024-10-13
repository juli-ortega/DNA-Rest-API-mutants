package julianortega.mutants.Repositories;

import julianortega.mutants.Entities.DNA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DNARepository extends JpaRepository<DNA, UUID> {
    List<DNA> findByIsMutantIs(boolean mutant);

    long countByIsMutant(boolean b);
}
