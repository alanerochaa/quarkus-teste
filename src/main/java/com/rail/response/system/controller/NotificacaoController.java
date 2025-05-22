package com.rail.response.system.controller;

import com.rail.response.system.DTOs.NotificacaoDTO;
import com.rail.response.system.exception.NotFoundException;
import com.rail.response.system.model.Notificacao;
import com.rail.response.system.service.NotificacaoService;
import com.rail.response.system.util.ValidatorUtils;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("notificacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Notificação", description = "Operações relacionadas à notificação")
public class NotificacaoController {

    @Inject
    NotificacaoService notificacaoService;

    @Path("/{id}")
    @GET
    @Operation(summary = "Buscar notificação por ID", description = "Retorna uma notificação pelo seu identificador")
    @APIResponse(responseCode = "200", description = "Notificação encontrada")
    @APIResponse(responseCode = "404", description = "Notificação não encontrada")
    public NotificacaoDTO buscarNotificacao(@RestPath long id) {
        var notificacao = notificacaoService.buscarNotificacaoPeloId(id);
        return mapNotificacao(notificacao);
    }

    @GET
    @Operation(summary = "Listar notificações", description = "Retorna a lista de todas as notificações")
    @APIResponse(responseCode = "200", description = "Lista de notificações")
    public List<NotificacaoDTO> listarNotificacoes() {
        return notificacaoService.listarNotificacoes().stream().map(this::mapNotificacao).collect(Collectors.toList());
    }

    @Path("/{id}")
    @PUT
    @Operation(summary = "Atualizar notificação", description = "Atualiza uma notificação existente")
    @APIResponse(responseCode = "200", description = "Notificação atualizada com sucesso")
    @APIResponse(responseCode = "404", description = "Notificação não encontrada")
    public NotificacaoDTO atualizarNotificacao(@RestPath long id, NotificacaoDTO dto) {
        ValidatorUtils.validate(dto);

        var notificacao = notificacaoService.alterarNotificacao(id, dto);
        return mapNotificacao(notificacao);
    }

    @POST
    @Operation(summary = "Cadastrar notificação", description = "Cadastra uma nova notificação")
    @APIResponse(responseCode = "201", description = "Notificação cadastrada com sucesso")
    public NotificacaoDTO cadastrarNotificacao(@Valid NotificacaoDTO dto) {
        ValidatorUtils.validate(dto);

        var notificacao = notificacaoService.cadastrarNotificacao(dto);
        return mapNotificacao(notificacao);
    }

    @Path("/{id}")
    @DELETE
    @Operation(summary = "Deletar notificação", description = "Remove uma notificação pelo ID")
    @APIResponse(responseCode = "204", description = "Notificação removida com sucesso")
    @APIResponse(responseCode = "404", description = "Notificação não encontrada")
    public void deletarNotificacao(@RestPath long id) {
        notificacaoService.deletarNotificacao(id);
    }

    private NotificacaoDTO mapNotificacao(Notificacao notificacao) {
        if (Objects.isNull(notificacao))  {
            throw new NotFoundException("Notificacao não encontrada para o ID fornecido");
        }

        return NotificacaoDTO.builder()
                .id(notificacao.getId())
                .conteudo(notificacao.getConteudo())
                .tipoNotificacao(notificacao.getTipoNotificacao())
                .prioridade(notificacao.getPrioridade())
                .tipoAlerta(notificacao.getTipoAlerta())
                .estacao(notificacao.getEstacao())
                .dataCriacao(notificacao.getDataCriacao())
                .dataAtualizacao(notificacao.getDataAtualizacao())
                .titulo(notificacao.getTitulo())
                .criticidade(notificacao.getCriticidade())
                .operador(notificacao.getOperador())
                .comentario(notificacao.getComentario())
                .status(notificacao.getStatus())
                .build();
    }
}
