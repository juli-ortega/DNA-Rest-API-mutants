package julianortega.mutants;


import julianortega.mutants.Entities.DNA;
import julianortega.mutants.Entities.Dto.DNAdto;
import julianortega.mutants.Repositories.DNARepository;
import julianortega.mutants.Services.Imp.DNAService;
import julianortega.mutants.Tools.RatioResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DNAServiceTest {

	@Mock
	private DNARepository dnaRepository;

	@InjectMocks
	private DNAService dnaService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSave() {
		DNAdto adnDto = new DNAdto();
		adnDto.setMutant(true);

		// Ejecutamos el método que queremos probar
		dnaService.Save(adnDto);

		// Verificamos que el método save del DNARepository haya sido llamado
		verify(dnaRepository, times(1)).save(any(DNA.class));
	}

	@Test
	void testIsMutantTrue() {
		String[] dna = {
				"ATGCGA",
				"CAGTGC",
				"TTATGT",
				"AGAAGG",
				"CCCCTA",
				"TCACTG"
		};

		boolean result = dnaService.isMutant(dna);
		assertTrue(result, "El ADN debería ser mutante.");
	}

	@Test
	void testIsMutantFalse() {
		String[] dna = {
				"ATGCGA",
				"CAGTGC",
				"TTATTT",
				"AGACGG",
				"GCGTCA",
				"TCACTG"
		};

		boolean result = dnaService.isMutant(dna);
		assertFalse(result, "El ADN no debería ser mutante.");
	}

	@Test
	void testIsMutantThrowsExceptionWhenDNANull() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			dnaService.isMutant(null);
		});

		assertEquals("El array no debe ser null", exception.getMessage());
	}

	@Test
	void testIsMutantThrowsExceptionWhenDNAIsNotNxN() {
		String[] invalidDna = {
				"ATGCGA",
				"CAGTGC",
				"TTATTT"
		};

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			dnaService.isMutant(invalidDna);
		});

		assertEquals("La secuencia de ADN debe ser una matriz NxN.", exception.getMessage());
	}

	@Test
	void testGetAllAdnsRatio() {
		// Configuramos mocks para simular el comportamiento del repository
		when(dnaRepository.count()).thenReturn(100L);
		when(dnaRepository.countByIsMutant(true)).thenReturn(40L);

		RatioResponse response = dnaService.getAllAdnsRatio();

		assertEquals(100, response.getDnasCounts());
		assertEquals(40, response.getMutantsCounts());
		assertEquals(0.4, response.getRatio(), 0.001); // Tolerancia para comparación de decimales
	}

	@Test
	void testGetAllAdnsRatioThrowsExceptionWhenNoMutants() {
		when(dnaRepository.count()).thenReturn(100L);
		when(dnaRepository.countByIsMutant(true)).thenReturn(0L);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			dnaService.getAllAdnsRatio();
		});

		assertEquals("No se puede dividir por 0", exception.getMessage());
	}

	@Test
	void testHasHorizontalSequence() {
		String[] dna = {"AAAA", "CAGT", "TTAT", "AGAG"};
		boolean result = dnaService.hasHorizontalSequence(dna, 0, 0, 4);
		assertTrue(result, "Debería detectar una secuencia horizontal.");
	}

	@Test
	void testHasVerticalSequence() {
		String[] dna = {"ATGC", "ATGC", "ATGC", "ATGC"};
		boolean result = dnaService.hasVerticalSequence(dna, 0, 0, 4);
		assertTrue(result, "Debería detectar una secuencia vertical.");
	}

	@Test
	void testHasDiagonalRightSequence() {
		String[] dna = {"ATGC", "CAGT", "TGAT", "CTGA"};
		boolean result = dnaService.hasDiagonalRightSequence(dna, 0, 0, 4);
		assertTrue(result, "Debería detectar una secuencia diagonal hacia la derecha.");
	}

	@Test
	void testHasDiagonalLeftSequence() {
		String[] dna = {
				"ATGA",  // Letra 'A' en la posición (0,3)
				"CAAA",  // Letra 'A' en la posición (1,2)
				"GATA",  // Letra 'A' en la posición (2,1)
				"ATAA"   // Letra 'A' en la posición (3,0)
		};
		boolean result = dnaService.hasDiagonalLeftSequence(dna, 0, 3, 4);
		assertTrue(result, "Debería detectar una secuencia diagonal hacia la izquierda.");
	}
}
