package com.example.backtracking.service;

import org.springframework.stereotype.Service;

import com.example.backtracking.dto.SolutionResponseDTO;
import com.example.backtracking.dto.ExecutionMetricsDTO;
import java.util.List;

@Service
public class RecursiveBacktrackingService {

    private static final long LOW_MASK = 0xFFFFFFFFL;
    private static final int SHIFT = 32;

    public SolutionResponseDTO solveNQueens(int n) {
        long startTime = System.nanoTime();

        long packed = backtrack(0, n, 0, 0, 0);

        long totalPlacements = packed >>> SHIFT;
        long totalSolutions = packed & LOW_MASK;

        double durationMs = (System.nanoTime() - startTime) / 1_000_000.0;

        ExecutionMetricsDTO metrics = new ExecutionMetricsDTO(
                totalPlacements,
                totalPlacements,
                totalSolutions,
                durationMs);

        return new SolutionResponseDTO(n, (int) totalSolutions, List.of(), metrics);
    }

    private long backtrack(int row, int n, int columns, int leftDiagonal, int rightDiagonal) {

        if (row == n) {
            return 1L;
        }

        long placements = 0L;
        long solutions = 0L;
        int available = ((1 << n) - 1) & ~(columns | leftDiagonal | rightDiagonal);

        while (available != 0) {
            int position = available & -available;
            available ^= position;

            long child = backtrack(row + 1, n,
                    columns | position,
                    (leftDiagonal | position) << 1,
                    (rightDiagonal | position) >> 1);

            placements += (child >>> SHIFT) + 1L;
            solutions += child & LOW_MASK;
        }

        return (placements << SHIFT) | solutions;
    }
}
