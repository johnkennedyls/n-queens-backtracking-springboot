package com.example.backtracking.service;

import org.springframework.stereotype.Service;

import com.example.backtracking.dto.ComparisonResultDTO;
import com.example.backtracking.dto.ComparisonResultDTO.ApproachResult;
import com.example.backtracking.dto.SolutionResponseDTO;

import java.util.function.Supplier;

/**
 * Ejecuta ambas estrategias de manejo de memoria varias veces y compara sus
 * tiempos de ejecución. El tiempo medido es el que cada servicio registra
 * internamente (metrics.executionTimeMs), de modo que la comparación aísla el
 * coste del algoritmo y de su política de snapshots.
 */
@Service
public class BacktrackingComparisonService {

    private final BacktrackingService sharedMutableService;
    private final RecursiveBacktrackingService recursiveService;

    public BacktrackingComparisonService(BacktrackingService sharedMutableService,
            RecursiveBacktrackingService recursiveService) {
        this.sharedMutableService = sharedMutableService;
        this.recursiveService = recursiveService;
    }

    /**
     * @param n    tamaño del tablero
     * @param runs número de ejecuciones medidas por estrategia
     */
    public ComparisonResultDTO compare(int n, int runs) {
        int effectiveRuns = Math.max(1, runs);

        ApproachResult shared = benchmark(
                "sharedMutable + clone()",
                () -> sharedMutableService.solveNQueens(n),
                effectiveRuns);

        ApproachResult recursive = benchmark(
                "recursiveState (snapshot por recursión)",
                () -> recursiveService.solveNQueens(n),
                effectiveRuns);

        boolean sharedIsFaster = shared.avgTimeMs() <= recursive.avgTimeMs();
        String fasterApproach = sharedIsFaster ? shared.name() : recursive.name();

        double fasterAvg = sharedIsFaster ? shared.avgTimeMs() : recursive.avgTimeMs();
        double slowerAvg = sharedIsFaster ? recursive.avgTimeMs() : shared.avgTimeMs();
        double speedupFactor = fasterAvg == 0.0 ? 1.0 : slowerAvg / fasterAvg;

        return new ComparisonResultDTO(
                n,
                effectiveRuns,
                shared,
                recursive,
                fasterApproach,
                speedupFactor);
    }

    /**
     * Hace una ejecución de calentamiento (para que el JIT compile el método) y
     * luego mide {@code runs} veces, calculando promedio, mínimo y máximo.
     */
    private ApproachResult benchmark(String name, Supplier<SolutionResponseDTO> solver, int runs) {
        // Warmup: no se cuenta en las estadísticas.
        SolutionResponseDTO sample = solver.get();

        double min = Double.MAX_VALUE;
        double max = 0.0;
        double sum = 0.0;

        for (int i = 0; i < runs; i++) {
            double timeMs = solver.get().metrics().executionTimeMs();
            min = Math.min(min, timeMs);
            max = Math.max(max, timeMs);
            sum += timeMs;
        }

        double avg = sum / runs;

        return new ApproachResult(
                name,
                sample.totalSolutions(),
                sample.metrics().totalEvaluations(),
                sample.metrics().totalBacktracks(),
                avg,
                min == Double.MAX_VALUE ? 0.0 : min,
                max);
    }
}
