package com.tiagogodinho.trabalhoweb.repository;

import com.tiagogodinho.trabalhoweb.model.Task;
import com.tiagogodinho.trabalhoweb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUsuario(Usuario usuario);
}
