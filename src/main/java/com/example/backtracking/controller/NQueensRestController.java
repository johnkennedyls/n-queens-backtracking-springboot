package com.example.backtracking.controller;

import com.example.backtracking.dto.SolutionResponseDTO;
import com.example.backtracking.service.BacktrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/queens")
public class NQueensRestController {

    private final BacktrackingService backtrackingService;

    public NQueensRestController(BacktrackingService backtrackingService) {
        this.backtrackingService = backtrackingService;
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
}