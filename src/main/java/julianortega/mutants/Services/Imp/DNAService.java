package julianortega.mutants.Services.Imp;
import julianortega.mutants.Entities.DNA;
import julianortega.mutants.Entities.Dto.DNAdto;
import julianortega.mutants.Repositories.DNARepository;
import julianortega.mutants.Services.IDNAService;
import julianortega.mutants.Tools.RatioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class DNAService implements IDNAService {

    private final DNARepository dnaRepository;

    @Autowired
    public DNAService(DNARepository dnaRepository) {
        this.dnaRepository = dnaRepository;
    }

    @Override
    public void Save(DNAdto adnDto) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DNA adn = DNA.builder()
                .isMutant(adnDto.isMutant())
                .created_at(timestamp)
                .updated_at(timestamp)
                .build();
        dnaRepository.save(adn);
    }

    @Override
    public boolean isMutant(String[] dna) {
        if (dna == null) {
            throw new IllegalArgumentException("El array no debe ser null");
        }

        int n = dna.length;

        if (n == 0 || dna[0].length() != n) {
            throw new IllegalArgumentException("La secuencia de ADN debe ser una matriz NxN.");
        }

        int sequenceCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (hasHorizontalSequence(dna, i, j, n) ||
                        hasVerticalSequence(dna, i, j, n) ||
                        hasDiagonalRightSequence(dna, i, j, n) ||
                        hasDiagonalLeftSequence(dna, i, j, n)) {
                    sequenceCount++;
                }

                // Si ya encontramos más de una secuencia, es mutante
                if (sequenceCount > 1) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasHorizontalSequence(String[] dna, int row, int col, int n) {
        if (col + 3 >= n) return false;
        char letter = dna[row].charAt(col);
        return letter == dna[row].charAt(col + 1) &&
                letter == dna[row].charAt(col + 2) &&
                letter == dna[row].charAt(col + 3);
    }

    @Override
    public boolean hasVerticalSequence(String[] dna, int row, int col, int n) {
        if (row + 3 >= n) return false;
        char letter = dna[row].charAt(col);
        return letter == dna[row + 1].charAt(col) &&
                letter == dna[row + 2].charAt(col) &&
                letter == dna[row + 3].charAt(col);
    }

    @Override
    public boolean hasDiagonalRightSequence(String[] dna, int row, int col, int n) {
        if (row + 3 >= n || col + 3 >= n) return false;
        char letter = dna[row].charAt(col);
        return letter == dna[row + 1].charAt(col + 1) &&
                letter == dna[row + 2].charAt(col + 2) &&
                letter == dna[row + 3].charAt(col + 3);
    }

    @Override
    public boolean hasDiagonalLeftSequence(String[] dna, int row, int col, int n) {
        if (row + 3 >= n || col - 3 < 0) return false;
        char letter = dna[row].charAt(col);
        return letter == dna[row + 1].charAt(col - 1) &&
                letter == dna[row + 2].charAt(col - 2) &&
                letter == dna[row + 3].charAt(col - 3);
    }

    @Override
    public RatioResponse getAllAdnsRatio() {
        long totalAdns = dnaRepository.count();
        long totalMutants = dnaRepository.countByIsMutant(true);

        if (totalMutants == 0) {
            throw new RuntimeException("No se puede dividir por 0"); // Evitar la division por 0
        }
        return RatioResponse.builder()
                .dnasCounts(totalAdns)
                .mutantsCounts(totalMutants)
                .ratio((double) totalMutants / (double) totalAdns) // Convertir a double para obtener el valor decimal
                .build();
    }
}


