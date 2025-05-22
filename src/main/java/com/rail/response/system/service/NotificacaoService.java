package com.rail.response.system.service;

import com.rail.response.system.DTOs.NotificacaoDTO;
import com.rail.response.system.enums.StatusNotificacao;
import com.rail.response.system.model.Notificacao;
import com.rail.response.system.repository.NotificacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class NotificacaoService {

    @Inject
    NotificacaoRepository notificacaoRepository;

    public Notificacao buscarNotificacaoPeloId(long id) {
        return notificacaoRepository.findById(id);
    }

    public List<Notificacao> listarNotificacoes() {
        return notificacaoRepository.listAll();
    }

    @Transactional
    public Notificacao cadastrarNotificacao(NotificacaoDTO dto) {
        var notificacao = Notificacao.builder()
                .titulo(dto.getTitulo())
                .tipoNotificacao(dto.getTipoNotificacao())
                .estacao(dto.getEstacao())
                .criticidade(dto.getCriticidade())
                .prioridade(dto.getPrioridade())
                .tipoAlerta(dto.getTipoAlerta())
                .status(StatusNotificacao.PENDENTE.name())
                .conteudo(dto.getConteudo())
                .build();

        notificacaoRepository.persist(notificacao);
        return notificacao;
    }

    @Transactional
    public Notificacao alterarNotificacao(long id, NotificacaoDTO dto) {
        var notificacao = notificacaoRepository.findById(id);
        if (notificacao == null) return null;

        notificacao.setConteudo(dto.getConteudo());
        notificacao.setDataAtualizacao(LocalDate.now());
        notificacao.setTitulo(dto.getTitulo());
        notificacao.setTipoNotificacao(dto.getTipoNotificacao());
        notificacao.setEstacao(dto.getEstacao());
        notificacao.setCriticidade(dto.getCriticidade());
        notificacao.setPrioridade(dto.getPrioridade());
        notificacao.setTipoAlerta(dto.getTipoAlerta());
        notificacao.setStatus(dto.getStatus());
        notificacao.setComentario(dto.getComentario());

        notificacaoRepository.persist(notificacao);

        return notificacao;
    }

    @Transactional
    public void deletarNotificacao(Long id) {
        notificacaoRepository.deleteById(id);
    }
}
