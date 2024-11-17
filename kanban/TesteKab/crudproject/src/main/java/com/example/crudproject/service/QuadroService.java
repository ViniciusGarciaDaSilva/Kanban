package com.example.crudproject.service;

import com.example.crudproject.model.Quadro;
import com.example.crudproject.repository.QuadroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuadroService {

    @Autowired
    private QuadroRepository quadroRepository;

    public List<Quadro> findAll() {
        return quadroRepository.findAll();
    }

    public Quadro findById(Long id) {
        return quadroRepository.findById(id).orElse(null);
    }

    public Quadro save(Quadro quadro) {
        return quadroRepository.save(quadro);
    }

    public void deleteById(Long id) {
        quadroRepository.deleteById(id);
    }
}
