package com.example.backtracking;

import com.example.backtracking.dto.ComparisonResultDTO;
import com.example.backtracking.dto.SolutionResponseDTO;
import com.example.backtracking.service.BacktrackingComparisonService;
import com.example.backtracking.service.BacktrackingService;
import com.example.backtracking.service.RecursiveBacktrackingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RecursiveBacktrackingServiceTest {

    private RecursiveBacktrackingService recursiveService;
    private BacktrackingService sharedMutableService;
    private BacktrackingComparisonService comparisonService;

    @BeforeEach
    void setUp() {
        recursiveService = new RecursiveBacktrackingService();
        sharedMutableService = new BacktrackingService();
        comparisonService = new BacktrackingComparisonService(sharedMutableService, recursiveService);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 0",
            "3, 0",
            "4, 2",
            "8, 92"
    })
    @DisplayName("La variante recursiva debe devolver el mismo número de soluciones válidas")
    void shouldReturnCorrectNumberOfSolutions(int n, int expectedSolutions) {
        SolutionResponseDTO result = recursiveService.solveNQueens(n);

        assertThat(result.totalSolutions()).isEqualTo(expectedSolutions);
        assertThat(result.metrics().totalSolutions()).isEqualTo(expectedSolutions);
    }

    @ParameterizedTest
    @CsvSource({ "4", "6", "8" })
    @DisplayName("Ambas estrategias deben coincidir en soluciones y backtracks, y la recursiva poda evaluaciones")
    void bothStrategiesShouldAgree(int n) {
        SolutionResponseDTO shared = sharedMutableService.solveNQueens(n);
        SolutionResponseDTO recursive = recursiveService.solveNQueens(n);

        assertThat(recursive.totalSolutions()).isEqualTo(shared.totalSolutions());
        assertThat(recursive.metrics().totalBacktracks())
                .isEqualTo(shared.metrics().totalBacktracks());
        assertThat(recursive.metrics().totalEvaluations())
                .isLessThanOrEqualTo(shared.metrics().totalEvaluations());
    }

    @ParameterizedTest
    @CsvSource({ "6", "8" })
    @DisplayName("La comparación debe reportar métricas completas y tiempos no negativos")
    void comparisonShouldReportMetrics(int n) {
        ComparisonResultDTO comparison = comparisonService.compare(n, 5);

        assertThat(comparison.boardSize()).isEqualTo(n);
        assertThat(comparison.runs()).isEqualTo(5);
        assertThat(comparison.sharedMutable().totalSolutions())
                .isEqualTo(comparison.recursiveState().totalSolutions());
        assertThat(comparison.sharedMutable().avgTimeMs()).isGreaterThanOrEqualTo(0.0);
        assertThat(comparison.recursiveState().avgTimeMs()).isGreaterThanOrEqualTo(0.0);
        assertThat(comparison.speedupFactor()).isGreaterThanOrEqualTo(1.0);
        assertThat(comparison.fasterApproach()).isNotBlank();
    }
}
