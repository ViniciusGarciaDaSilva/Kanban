package com.example.crudproject.controller;

import com.example.crudproject.model.Coluna;
import com.example.crudproject.model.Quadro;
import com.example.crudproject.service.QuadroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quadros")
public class QuadroController {

    @Autowired
    private QuadroService quadroService;

    @GetMapping
    public List<Quadro> getAllQuadros() {
        return quadroService.findAll();
    }

    @GetMapping("/{id}")
    public Quadro getQuadroById(@PathVariable Long id) {
        return quadroService.findById(id);
    }

    @PostMapping
    public Quadro createQuadro(@RequestBody Quadro quadro) {
        return quadroService.save(quadro);
    }

    @PutMapping("/{id}")
    public Quadro updateQuadro(@PathVariable Long id, @RequestBody Quadro quadro) {
        Quadro existingQuadro = quadroService.findById(id);
        if (existingQuadro != null) {
            existingQuadro.setNome(quadro.getNome());
            return quadroService.save(existingQuadro);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteQuadro(@PathVariable Long id) {
        quadroService.deleteById(id);
    }

    @GetMapping("/{quadroId}/colunas")
    public List<Coluna> getColunasByQuadro(@PathVariable Long quadroId) {
        Quadro quadro = quadroService.findById(quadroId);
        return quadro != null ? quadro.getColunas() : null;
    }
}
