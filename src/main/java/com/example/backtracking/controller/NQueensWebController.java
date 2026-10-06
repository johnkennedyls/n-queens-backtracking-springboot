package com.example.backtracking.controller;

import com.example.backtracking.dto.SolutionResponseDTO;
import com.example.backtracking.service.BacktrackingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NQueensWebController {

    private final BacktrackingService backtrackingService;

    public NQueensWebController(BacktrackingService backtrackingService) {
        this.backtrackingService = backtrackingService;
    }

    // Ruta 1: Carga inicial de la página
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("boardSize", 8);
        return "queens";
    }

    // Ruta 2: Procesar formulario y resolver N-Reinas
    @PostMapping("/solve")
    public String solve(@RequestParam("boardSize") int boardSize, Model model) {
        if (boardSize < 1 || boardSize > 12) {
            model.addAttribute("error", "Por favor ingresa un tamaño de tablero entre 1 y 12.");
            model.addAttribute("boardSize", boardSize);
            return "queens";
        }

        SolutionResponseDTO result = backtrackingService.solveNQueens(boardSize);
        model.addAttribute("result", result);
        model.addAttribute("boardSize", boardSize);
        return "queens";
    }
}