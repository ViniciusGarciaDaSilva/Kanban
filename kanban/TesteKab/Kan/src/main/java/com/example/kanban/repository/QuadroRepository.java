package com.example.kanban.repository;

import com.example.kanban.model.Quadro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuadroRepository extends JpaRepository<Quadro, Long> {
}
