package com.example.backtracking;

import com.example.backtracking.dto.SolutionResponseDTO;
import com.example.backtracking.service.BacktrackingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class BacktrackingServiceTest {

    private BacktrackingService backtrackingService;

    @BeforeEach
    void setUp() {
        backtrackingService = new BacktrackingService();
    }

    @ParameterizedTest
    @CsvSource({
        "1, 1",  // 1 Reina -> 1 Solución
        "2, 0",  // 2 Reinas -> 0 Soluciones
        "3, 0",  // 3 Reinas -> 0 Soluciones
        "4, 2",  // 4 Reinas -> 2 Soluciones
        "8, 92"  // 8 Reinas -> 92 Soluciones exactas
    })
    @DisplayName("Debe verificar el número exacto de soluciones matemáticamente válidas para N")
    void shouldReturnCorrectNumberOfSolutions(int n, int expectedSolutions) {
        SolutionResponseDTO result = backtrackingService.solveNQueens(n);

        assertThat(result.totalSolutions()).isEqualTo(expectedSolutions);
        assertThat(result.solutions()).hasSize(expectedSolutions);
        assertThat(result.metrics().executionTimeMs()).isGreaterThanOrEqualTo(0.0);
    }
}