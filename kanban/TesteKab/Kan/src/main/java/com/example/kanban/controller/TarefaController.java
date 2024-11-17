package com.example.kanban.controller;

import com.example.kanban.model.Tarefa;
import com.example.kanban.model.Coluna;
import com.example.kanban.service.TarefaService;
import com.example.kanban.service.ColunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private ColunaService colunaService;

    // Criar uma nova tarefa na coluna "A Fazer"
    @PostMapping
    public ResponseEntity<Tarefa> createTask(@RequestBody Tarefa tarefa) {
        if (tarefa.getTitulo() == null || tarefa.getTitulo().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Encontrar ou criar a coluna "A Fazer"
        Coluna colunaAFazer = colunaService.findByNome("A Fazer");
        if (colunaAFazer == null) {
            colunaAFazer = new Coluna();
            colunaAFazer.setNome("A Fazer");
            colunaAFazer = colunaService.save(colunaAFazer);
        }

        tarefa.setColuna(colunaAFazer);
        tarefa.setStatus("A Fazer");
        tarefa.setDataCriacao(new Date());

        Tarefa savedTarefa = tarefaService.save(tarefa);
        return ResponseEntity.ok(savedTarefa);
    }

    // Listar todas as tarefas organizadas por coluna
    @GetMapping
    public ResponseEntity<Map<String, List<Tarefa>>> getAllTasksOrganizedByColumn(
            @RequestParam(required = false) String prioridade,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataLimite
    ) {
        List<Tarefa> tarefas = tarefaService.findAll();

        // Aplicar filtros se necessário
        if (prioridade != null) {
            tarefas.removeIf(tarefa -> !tarefa.getPrioridade().equalsIgnoreCase(prioridade));
        }
        if (dataLimite != null) {
            tarefas.removeIf(tarefa -> !tarefa.getDataLimite().equals(dataLimite));
        }

        // Organizar tarefas por coluna
        Map<String, List<Tarefa>> tarefasPorColuna = new HashMap<>();
        for (Tarefa tarefa : tarefas) {
            String nomeColuna = tarefa.getColuna().getNome();
            tarefasPorColuna.computeIfAbsent(nomeColuna, k -> new ArrayList<>()).add(tarefa);
        }

        // Ordenar tarefas dentro de cada coluna por prioridade
        for (List<Tarefa> listaTarefas : tarefasPorColuna.values()) {
            listaTarefas.sort(Comparator.comparing(Tarefa::getPrioridade));
        }

        return ResponseEntity.ok(tarefasPorColuna);
    }

    // Mover uma tarefa para a próxima coluna
    @PutMapping("/{id}/move")
    public ResponseEntity<Tarefa> moveTaskToNextColumn(@PathVariable Long id) {
        Tarefa tarefa = tarefaService.findById(id);
        if (tarefa == null) {
            return ResponseEntity.notFound().build();
        }

        String nomeColunaAtual = tarefa.getColuna().getNome();
        String nomeProximaColuna;

        switch (nomeColunaAtual) {
            case "A Fazer":
                nomeProximaColuna = "Em Progresso";
                break;
            case "Em Progresso":
                nomeProximaColuna = "Concluído";
                break;
            case "Concluído":
                return ResponseEntity.badRequest().body(null); // Não pode mover além de "Concluído"
            default:
                return ResponseEntity.badRequest().body(null);
        }

        // Verifica se a próxima coluna existe; se não, cria uma nova
        Coluna proximaColuna = colunaService.findByNome(nomeProximaColuna);
        if (proximaColuna == null) {
            proximaColuna = new Coluna();
            proximaColuna.setNome(nomeProximaColuna);
            proximaColuna = colunaService.save(proximaColuna);
        }

        // Atualiza a coluna e o status da tarefa
        tarefa.setColuna(proximaColuna);
        tarefa.setStatus(proximaColuna.getNome()); // Atualiza o status para o nome da nova coluna
        tarefa = tarefaService.save(tarefa);

        return ResponseEntity.ok(tarefa);
    }

    // Atualizar uma tarefa
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> updateTask(@PathVariable Long id, @RequestBody Tarefa updatedTarefa) {
        Tarefa existingTarefa = tarefaService.findById(id);
        if (existingTarefa == null) {
            return ResponseEntity.notFound().build();
        }

        existingTarefa.setTitulo(updatedTarefa.getTitulo());
        existingTarefa.setDescricao(updatedTarefa.getDescricao());
        existingTarefa.setPrioridade(updatedTarefa.getPrioridade());
        existingTarefa.setDataLimite(updatedTarefa.getDataLimite());

        tarefaService.save(existingTarefa);
        return ResponseEntity.ok(existingTarefa);
    }

    // Excluir uma tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (tarefaService.findById(id) != null) {
            tarefaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
