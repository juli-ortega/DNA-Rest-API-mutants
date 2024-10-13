package julianortega.mutants.Services;

import julianortega.mutants.Entities.Dto.DNAdto;
import julianortega.mutants.Tools.RatioResponse;

public interface IDNAService {
    boolean isMutant(String[] dna);

    boolean hasHorizontalSequence(String[] dna, int row, int col, int n);

    boolean hasVerticalSequence(String[] dna, int row, int col, int n);

    boolean hasDiagonalRightSequence(String[] dna, int row, int col, int n);

    boolean hasDiagonalLeftSequence(String[] dna, int row, int col, int n);

    void Save(DNAdto dto);

    RatioResponse getAllAdnsRatio();

}
