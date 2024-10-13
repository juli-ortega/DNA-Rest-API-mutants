package julianortega.mutants.Controllers;

import julianortega.mutants.Entities.Dto.DNAdto;
import julianortega.mutants.Services.Imp.DNAService;
import julianortega.mutants.Tools.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/mutant")
@RequiredArgsConstructor
public class DNAController {

    private final DNAService dnaService;

    @GetMapping("/stats")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().body(dnaService.getAllAdnsRatio().toString());
    }

    @PostMapping("")
    public ResponseEntity<?> sendDna(@RequestBody DNAdto dnaDto) {
        try {
            boolean isMutant = dnaService.isMutant(dnaDto.getDna());
            dnaDto.setMutant(isMutant);
            dnaService.Save(dnaDto);
            String message = isMutant ? "El ADN corresponde a un mutante." : "El ADN NO corresponde a un mutante.";
            return ResponseEntity.ok().body(message);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(403)
                    .message(e.getMessage())
                    .stackTrace(e.getStackTrace())
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

}
