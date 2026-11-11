package com.example.backtracking.controller;

import com.example.backtracking.dto.ComparisonResultDTO;
import com.example.backtracking.dto.SolutionResponseDTO;
import com.example.backtracking.service.BacktrackingComparisonService;
import com.example.backtracking.service.BacktrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/queens")
public class NQueensRestController {

    private final BacktrackingService backtrackingService;
    private final BacktrackingComparisonService comparisonService;

    public NQueensRestController(BacktrackingService backtrackingService,
            BacktrackingComparisonService comparisonService) {
        this.backtrackingService = backtrackingService;
        this.comparisonService = comparisonService;
    }

    // Ruta API: GET /api/v1/queens/solve?n=8
    @GetMapping("/solve")
    public ResponseEntity<SolutionResponseDTO> solveNQueens(@RequestParam(defaultValue = "8") int n) {
        if (n < 1 || n > 13) {
            return ResponseEntity.badRequest().build();
        }
        SolutionResponseDTO response = backtrackingService.solveNQueens(n);
        return ResponseEntity.ok(response);
    }

    // Ruta API: GET /api/v1/queens/compare?n=8&runs=20
    // Compara los tiempos de ejecución de la estrategia con clone() vs. la
    // recursiva.
    @GetMapping("/compare")
    public ResponseEntity<ComparisonResultDTO> compare(@RequestParam(defaultValue = "8") int n,
            @RequestParam(defaultValue = "20") int runs) {
        if (n < 1 || n > 13) {
            return ResponseEntity.badRequest().build();
        }
        ComparisonResultDTO response = comparisonService.compare(n, runs);
        return ResponseEntity.ok(response);
    }
}