package julianortega.mutants.Tools;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    Integer status;
    String message;
    StackTraceElement[] stackTrace;
}
