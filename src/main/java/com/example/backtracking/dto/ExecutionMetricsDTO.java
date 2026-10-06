package com.example.backtracking.dto;

public record ExecutionMetricsDTO(
        long totalEvaluations,
        long totalBacktracks,
        long totalSolutions,
        double executionTimeMs
) {}