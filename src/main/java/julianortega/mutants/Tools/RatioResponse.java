package julianortega.mutants.Tools;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatioResponse {
    private long mutantsCounts;
    private long dnasCounts;
    private double ratio;

    @Override
    public String toString() {
        return "Cantidad de Mutantes: " + mutantsCounts +
                ", Cantidad de ADNs: " + dnasCounts +
                ", Ratio: " + ratio;
    }

}
