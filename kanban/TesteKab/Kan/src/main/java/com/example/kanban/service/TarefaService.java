package com.example.kanban.service;

import com.example.kanban.model.Tarefa;
import com.example.kanban.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> findAll() {
        return tarefaRepository.findAll();
    }

    public Tarefa findById(Long id) {
        return tarefaRepository.findById(id).orElse(null);
    }

    public Tarefa save(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public void deleteById(Long id) {
        tarefaRepository.deleteById(id);
    }

    public List<Tarefa> findByPrioridade(String prioridade) {
        return tarefaRepository.findByPrioridade(prioridade);
    }

    public List<Tarefa> findByDataLimite(Date dataLimite) {
        return tarefaRepository.findByDataLimite(dataLimite);
    }
}
