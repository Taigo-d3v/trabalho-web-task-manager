package com.tiagogodinho.trabalhoweb.controller;

import com.tiagogodinho.trabalhoweb.dto.TaskRequestDTO;
import com.tiagogodinho.trabalhoweb.dto.TaskResponseDTO;
import com.tiagogodinho.trabalhoweb.model.Task;
import com.tiagogodinho.trabalhoweb.model.Usuario;
import com.tiagogodinho.trabalhoweb.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Nosso Controller para o CRUD das tarefas. 
// Só entra aqui quem tiver com o Token JWT válido sendo passado no header.
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // Fiz esse método pra facilitar na hora de pegar quem é o usuário que está fazendo a requisição agora
    private Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // Rota pra listar as tarefas. Detalhe: só traz as tarefas do cara que tá logado
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> listarTodas() {
        Usuario usuario = getUsuarioLogado();
        
        // Puxa do banco as tarefas filtrando pelo usuário (usando o relacionamento que fizemos)
        List<TaskResponseDTO> tasks = taskRepository.findAllByUsuario(usuario).stream()
                .map(TaskResponseDTO::new).toList();
                
        return ResponseEntity.ok(tasks);
    }

    // Rota para criar uma tarefa nova
    @PostMapping
    public ResponseEntity<TaskResponseDTO> criarTask(@RequestBody @Valid TaskRequestDTO data) {
        Usuario usuario = getUsuarioLogado();
        
        // Cria a tarefa já amarrando ela com o usuário logado
        Task novaTask = new Task(data.title(), data.description(), data.status(), usuario);
        taskRepository.save(novaTask);
        
        return ResponseEntity.status(201).body(new TaskResponseDTO(novaTask));
    }

    // Rota pra atualizar uma tarefa que já existe
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> atualizarTask(@PathVariable Long id, @RequestBody @Valid TaskRequestDTO data) {
        Usuario usuario = getUsuarioLogado();
        Optional<Task> taskOptional = taskRepository.findById(id);

        // Uma validação de segurança: confere se a tarefa existe e se ela realmente é da pessoa que tá querendo editar
        if (taskOptional.isEmpty() || !taskOptional.get().getUsuario().getId().equals(usuario.getId())) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskOptional.get();
        task.setTitle(data.title());
        task.setDescription(data.description());
        task.setStatus(data.status());
        taskRepository.save(task);

        return ResponseEntity.ok(new TaskResponseDTO(task));
    }

    // Rota pra excluir a tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity deletarTask(@PathVariable Long id) {
        Usuario usuario = getUsuarioLogado();
        Optional<Task> taskOptional = taskRepository.findById(id);

        // Mesma validação: só deixa apagar se a tarefa for dele mesmo
        if (taskOptional.isEmpty() || !taskOptional.get().getUsuario().getId().equals(usuario.getId())) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.delete(taskOptional.get());
        return ResponseEntity.noContent().build();
    }
}
