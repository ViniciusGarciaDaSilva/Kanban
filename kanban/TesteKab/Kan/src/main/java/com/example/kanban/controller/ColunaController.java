package com.example.kanban.controller;

import com.example.kanban.model.Coluna;
import com.example.kanban.model.Quadro;
import com.example.kanban.service.ColunaService;
import com.example.kanban.service.QuadroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colunas")
public class ColunaController {

    @Autowired
    private ColunaService colunaService;

    @Autowired
    private QuadroService quadroService;

    @GetMapping
    public List<Coluna> getAllColunas() {
        return colunaService.findAll();
    }

    @GetMapping("/{id}")
    public Coluna getColunaById(@PathVariable Long id) {
        return colunaService.findById(id);
    }

    @PostMapping("/{quadroId}")
    public Coluna addColunaToQuadro(@PathVariable Long quadroId, @RequestBody Coluna coluna) {
        Quadro quadro = quadroService.findById(quadroId);
        if (quadro != null) {
            coluna.setQuadro(quadro);
            return colunaService.save(coluna);
        }
        return null;
    }

    @PutMapping("/{colunaId}")
    public Coluna updateColuna(@PathVariable Long colunaId, @RequestBody Coluna coluna) {
        Coluna existingColuna = colunaService.findById(colunaId);
        if (existingColuna != null) {
            existingColuna.setNome(coluna.getNome());
            return colunaService.save(existingColuna);
        }
        return null;
    }

    @DeleteMapping("/{colunaId}")
    public void deleteColuna(@PathVariable Long colunaId) {
        colunaService.deleteById(colunaId);
    }
}
