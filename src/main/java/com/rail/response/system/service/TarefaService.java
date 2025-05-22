package com.rail.response.system.service;

import com.rail.response.system.DTOs.TarefaDTO;
import com.rail.response.system.model.Tarefa;
import com.rail.response.system.repository.TarefaRepository;
import com.rail.response.system.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TarefaService {

    @Inject
    TarefaRepository tarefaRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    public Tarefa buscarTarefaPeloId(long id) {
        return tarefaRepository.findById(id);
    }

    @Transactional
    public Tarefa cadastrarTarefa(TarefaDTO tarefaDTO) {
        var usuario = usuarioRepository.findById(Long.valueOf(tarefaDTO.getIdUsuario()));
        if (usuario == null) {
            throw new WebApplicationException("Usuário não encontrado", 404);
        }

        var tarefa = Tarefa.builder()
                .titulo(tarefaDTO.getTitulo())
                .descricao(tarefaDTO.getDescricao())
                .status(tarefaDTO.getStatus())
                .usuario(usuario)
                .build();

        tarefaRepository.persist(tarefa);
        return tarefa;
    }

    @Transactional
    public Tarefa alterarTarefa(long id, TarefaDTO dto) {
        Tarefa tarefa = tarefaRepository.findById(id);
        if (tarefa == null) {
            throw new WebApplicationException("Tarefa não encontrada", 404);
        }

        var usuario = usuarioRepository.findById(Long.valueOf(dto.getIdUsuario()));
        if (usuario == null) {
            throw new WebApplicationException("Usuário não encontrado", 404);
        }

        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setStatus(dto.getStatus());
        tarefa.setDataAtualizacao(LocalDate.now());
        tarefa.setUsuario(usuario);

        return tarefa;
    }

    @Transactional
    public void deletarTarefa(Long id) {
        boolean deleted = tarefaRepository.deleteById(id);
        if (!deleted) {
            throw new WebApplicationException("Tarefa não encontrada", 404);
        }
    }

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.listAll();
    }
}
