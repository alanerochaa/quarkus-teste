package com.rail.response.system.controller;

import com.rail.response.system.DTOs.TipoNotificacaoDTO;
import com.rail.response.system.exception.NotFoundException;
import com.rail.response.system.model.TipoNotificacao;
import com.rail.response.system.service.TipoNotificacaoService;
import com.rail.response.system.util.ValidatorUtils;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("tipo-notificacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "TipoNotificacao", description = "Operações relacionadas ao tipo de notificação")
public class TipoNotificacaoController {

    @Inject
    TipoNotificacaoService service;

    @GET
    @Operation(summary = "Listar tipos de notificação", description = "Retorna a lista de todos os tipos de notificação")
    @APIResponse(responseCode = "200", description = "Lista de tipos de notificação")
    public List<TipoNotificacaoDTO> listarTodos() {
        return service.listarTodos().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar tipo de notificação por ID", description = "Retorna um tipo de notificação pelo seu identificador")
    @APIResponse(responseCode = "200", description = "Tipo de notificação encontrado")
    @APIResponse(responseCode = "404", description = "Tipo de notificação não encontrado")
    public TipoNotificacaoDTO buscarPorId(@RestPath long id) {
        var tipo = service.buscarPorId(id);
        return mapToDTO(tipo);
    }

    @POST
    @Operation(summary = "Cadastrar tipo de notificação", description = "Cadastra um novo tipo de notificação")
    @APIResponse(responseCode = "201", description = "Tipo de notificação cadastrado com sucesso")
    public TipoNotificacaoDTO cadastrar(TipoNotificacaoDTO dto) {
        ValidatorUtils.validate(dto);

        var novo = service.cadastrar(dto);
        return mapToDTO(novo);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar tipo de notificação", description = "Atualiza um tipo de notificação existente")
    @APIResponse(responseCode = "200", description = "Tipo de notificação atualizado com sucesso")
    @APIResponse(responseCode = "404", description = "Tipo de notificação não encontrado")
    public TipoNotificacaoDTO atualizar(@RestPath long id, TipoNotificacaoDTO dto) {
        ValidatorUtils.validate(dto);

        var atualizado = service.atualizar(id, dto);
        return mapToDTO(atualizado);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar tipo de notificação", description = "Remove um tipo de notificação pelo ID")
    @APIResponse(responseCode = "204", description = "Tipo de notificação removido com sucesso")
    @APIResponse(responseCode = "404", description = "Tipo de notificação não encontrado")
    public void deletar(@RestPath long id) {
        service.deletar(id);
    }

    private TipoNotificacaoDTO mapToDTO(TipoNotificacao tipo) {
        if (Objects.isNull(tipo))  {
            throw new NotFoundException("TipoNotificacao não encontrada para o ID fornecido");
        }

        return TipoNotificacaoDTO.builder()
                .id(tipo.getId())
                .tp(tipo.getTp())
                .dataCriacao(tipo.getDataCriacao())
                .dataAtualizacao(tipo.getDataAtualizacao())
                .idNotificacao(tipo.getNotificacao().getId())
                .build();
    }
}
