package com.example.backtracking.dto;

import java.util.List;

public record SolutionResponseDTO(
        int boardSize,
        int totalSolutions,
        List<List<Integer>> solutions,
        ExecutionMetricsDTO metrics
) {}