package com.example.kanban.repository;

import com.example.kanban.model.Coluna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColunaRepository extends JpaRepository<Coluna, Long> {
    Coluna findByNome(String nome);
}
