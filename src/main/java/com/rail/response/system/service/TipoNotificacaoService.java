package com.rail.response.system.service;

import com.rail.response.system.DTOs.TipoNotificacaoDTO;
import com.rail.response.system.exception.BadRequestException;
import com.rail.response.system.exception.NotFoundException;
import com.rail.response.system.model.Notificacao;
import com.rail.response.system.model.TipoNotificacao;
import com.rail.response.system.repository.TipoNotificacaoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TipoNotificacaoService {

    @Inject
    TipoNotificacaoRepository repository;

    @Inject
    NotificacaoService notificacaoService;

    public List<TipoNotificacao> listarTodos() {
        return repository.listAll(Sort.by("id"));
    }

    public TipoNotificacao buscarPorId(long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoNotificacao cadastrar(TipoNotificacaoDTO dto) {
        validarDto(dto);

        Notificacao notificacao = notificacaoService.buscarNotificacaoPeloId(dto.getIdNotificacao());
        if (notificacao == null) {
            throw new NotFoundException("Notificação com id " + dto.getIdNotificacao() + " não encontrada.");
        }

        TipoNotificacao tipo = TipoNotificacao.builder()
                .tp(dto.getTp())
                .notificacao(notificacao)
                .build();

        repository.persist(tipo);
        return tipo;
    }

    @Transactional
    public TipoNotificacao atualizar(long id, TipoNotificacaoDTO dto) {
        validarDto(dto);

        TipoNotificacao tipo = repository.findById(id);
        if (tipo == null) {
            throw new NotFoundException("TipoNotificacao com id " + id + " não encontrado.");
        }

        tipo.setTp(dto.getTp());
        tipo.setDataAtualizacao(LocalDate.now());

        if (dto.getIdNotificacao() != null) {
            Notificacao notificacao = notificacaoService.buscarNotificacaoPeloId(dto.getIdNotificacao());
            if (notificacao == null) {
                throw new NotFoundException("Notificação com id " + dto.getIdNotificacao() + " não encontrada.");
            }
            tipo.setNotificacao(notificacao);
        }

        return tipo;
    }

    @Transactional
    public void deletar(long id) {
        repository.deleteById(id);
    }

    private void validarDto(TipoNotificacaoDTO dto) {
        if (dto == null) {
            throw new BadRequestException("DTO não pode ser nulo.");
        }
        if (dto.getTp() == null || dto.getTp().trim().isEmpty()) {
            throw new BadRequestException("O campo 'tp' é obrigatório e não pode estar vazio.");
        }
        if (dto.getIdNotificacao() == null) {
            throw new BadRequestException("O campo 'idNotificacao' é obrigatório.");
        }
    }
}
