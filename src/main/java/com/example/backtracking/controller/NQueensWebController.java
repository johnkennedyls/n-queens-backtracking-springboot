package com.example.backtracking.controller;

import com.example.backtracking.dto.ComparisonResultDTO;
import com.example.backtracking.dto.SolutionResponseDTO;
import com.example.backtracking.service.BacktrackingComparisonService;
import com.example.backtracking.service.BacktrackingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NQueensWebController {

    private final BacktrackingService backtrackingService;
    private final BacktrackingComparisonService comparisonService;

    public NQueensWebController(BacktrackingService backtrackingService,
            BacktrackingComparisonService comparisonService) {
        this.backtrackingService = backtrackingService;
        this.comparisonService = comparisonService;
    }

    // Ruta 1: Carga inicial de la página
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("boardSize", 8);
        model.addAttribute("runs", 20);
        return "queens";
    }

    // Ruta 2: Procesar formulario, resolver N-Reinas y comparar estrategias
    @PostMapping("/solve")
    public String solve(@RequestParam("boardSize") int boardSize,
            @RequestParam(name = "runs", defaultValue = "20") int runs,
            Model model) {
        if (boardSize < 1 || boardSize > 12) {
            model.addAttribute("error", "Por favor ingresa un tamaño de tablero entre 1 y 12.");
            model.addAttribute("boardSize", boardSize);
            model.addAttribute("runs", runs);
            return "queens";
        }

        int safeRuns = Math.min(Math.max(runs, 1), 200);

        SolutionResponseDTO result = backtrackingService.solveNQueens(boardSize);
        ComparisonResultDTO comparison = comparisonService.compare(boardSize, safeRuns);

        model.addAttribute("result", result);
        model.addAttribute("comparison", comparison);
        model.addAttribute("boardSize", boardSize);
        model.addAttribute("runs", safeRuns);
        return "queens";
    }
}