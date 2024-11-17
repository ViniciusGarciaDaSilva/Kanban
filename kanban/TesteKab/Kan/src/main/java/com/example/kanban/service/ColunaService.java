package com.example.kanban.service;

import com.example.kanban.model.Coluna;
import com.example.kanban.repository.ColunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColunaService {

    @Autowired
    private ColunaRepository colunaRepository;

    public List<Coluna> findAll() {
        return colunaRepository.findAll();
    }

    public Coluna findById(Long id) {
        return colunaRepository.findById(id).orElse(null);
    }

    public Coluna save(Coluna coluna) {
        return colunaRepository.save(coluna);
    }

    public void deleteById(Long id) {
        colunaRepository.deleteById(id);
    }

    public Coluna findByNome(String nome) {
        return colunaRepository.findByNome(nome);
    }
}
