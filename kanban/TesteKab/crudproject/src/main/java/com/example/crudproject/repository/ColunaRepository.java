package com.example.crudproject.repository;

import com.example.crudproject.model.Coluna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColunaRepository extends JpaRepository<Coluna, Long> {
    Coluna findByNome(String nome);
}
