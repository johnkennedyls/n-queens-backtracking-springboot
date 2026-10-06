package com.example.backtracking.service;

import org.springframework.stereotype.Service;

import com.example.backtracking.dto.SolutionResponseDTO;
import com.example.backtracking.dto.ExecutionMetricsDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BacktrackingService {

    public SolutionResponseDTO solveNQueens(int n) {
        long startTime = System.nanoTime();

        List<int[]> rawSolutions = new ArrayList<>();
        int[] board = new int[n];
        Arrays.fill(board, -1);

        AtomicLong evaluationsCounter = new AtomicLong(0);
        AtomicLong backtracksCounter = new AtomicLong(0);

        backtrack(0, n, board, rawSolutions, evaluationsCounter, backtracksCounter);

        long durationNs = System.nanoTime() - startTime;
        double durationMs = durationNs / 1_000_000.0;

        // Transformación de List<int[]> a List<List<Integer>> para compatibilidad con el DTO
        List<List<Integer>> formattedSolutions = new ArrayList<>();
        for (int[] sol : rawSolutions) {
            List<Integer> solutionList = new ArrayList<>();
            for (int val : sol) {
                solutionList.add(val);
            }
            formattedSolutions.add(solutionList);
        }

        ExecutionMetricsDTO metrics = new ExecutionMetricsDTO(
                evaluationsCounter.get(),
                backtracksCounter.get(),
                rawSolutions.size(),
                durationMs
        );

        return new SolutionResponseDTO(n, formattedSolutions.size(), formattedSolutions, metrics);
    }

    private void backtrack(int row, int n, int[] board, List<int[]> solutions,
                           AtomicLong evaluations, AtomicLong backtracks) {

        if (row == n) {
            solutions.add(board.clone());
            return;
        }

        for (int col = 0; col < n; col++) {
            evaluations.incrementAndGet();

            if (isSafe(board, row, col)) {
                board[row] = col;
                backtrack(row + 1, n, board, solutions, evaluations, backtracks);
                board[row] = -1;
                backtracks.incrementAndGet();
            }
        }
    }

    private boolean isSafe(int[] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            int placedCol = board[i];

            if (placedCol == col) return false;

            if (Math.abs(placedCol - col) == Math.abs(i - row)) return false;
        }
        return true;
    }
}