package com.example.crudproject.repository;

import com.example.crudproject.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByPrioridade(String prioridade);
    List<Tarefa> findByDataLimite(Date dataLimite);
}
