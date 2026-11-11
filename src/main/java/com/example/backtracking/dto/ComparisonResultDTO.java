package com.example.backtracking.dto;

/**
 * Resultado de comparar los tiempos de ejecución de las dos estrategias de
 * manejo de memoria del tablero:
 * - sharedMutable: {@code BacktrackingService} (un arreglo compartido +
 * clone()).
 * - recursiveState: {@code RecursiveBacktrackingService} (estado por rama vía
 * recursión).
 *
 * Los tiempos se promedian sobre varias ejecuciones (runs) para reducir el
 * ruido
 * del JIT y del scheduler.
 */
public record ComparisonResultDTO(
        int boardSize,
        int runs,
        ApproachResult sharedMutable,
        ApproachResult recursiveState,
        String fasterApproach,
        double speedupFactor) {
    public record ApproachResult(
            String name,
            long totalSolutions,
            long totalEvaluations,
            long totalBacktracks,
            double avgTimeMs,
            double minTimeMs,
            double maxTimeMs) {
    }
}
