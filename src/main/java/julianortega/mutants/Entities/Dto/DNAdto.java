package julianortega.mutants.Entities.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DNAdto {
    public String[] dna;
    public boolean isMutant;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Timestamp deleted_at;
}
