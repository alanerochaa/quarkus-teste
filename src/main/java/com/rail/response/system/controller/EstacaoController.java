package com.rail.response.system.controller;

import com.rail.response.system.DTOs.EstacaoDTO;
import com.rail.response.system.exception.NotFoundException;
import com.rail.response.system.model.Estacao;
import com.rail.response.system.service.EstacaoService;
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

@Path("estacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Estação", description = "Operações relacionadas à estação")
public class EstacaoController {

    @Inject
    EstacaoService estacaoService;

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar estação por ID", description = "Retorna uma estação pelo seu identificador")
    @APIResponse(responseCode = "200", description = "Estação encontrada")
    @APIResponse(responseCode = "404", description = "Estação não encontrada")
    public EstacaoDTO getEstacao(@RestPath long id) {
        return mapDTO(estacaoService.buscarEstacaoPorId(id));
    }

    @GET
    @Operation(summary = "Listar estações", description = "Retorna a lista de todas as estações")
    @APIResponse(responseCode = "200", description = "Lista de estações")
    public List<EstacaoDTO> listar() {
        return estacaoService.listarEstacoes()
                .stream()
                .map(this::mapDTO)
                .collect(Collectors.toList());
    }

    @POST
    @Operation(summary = "Cadastrar estação", description = "Cadastra uma nova estação")
    @APIResponse(responseCode = "201", description = "Estação cadastrada com sucesso")
    public EstacaoDTO cadastrar(EstacaoDTO dto) {
        ValidatorUtils.validate(dto);
        return mapDTO(estacaoService.cadastrarEstacao(dto));
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar estação", description = "Atualiza uma estação existente")
    @APIResponse(responseCode = "200", description = "Estação atualizada com sucesso")
    @APIResponse(responseCode = "404", description = "Estação não encontrada")
    public EstacaoDTO atualizar(@RestPath long id, EstacaoDTO dto) {
        ValidatorUtils.validate(dto);
        return mapDTO(estacaoService.atualizarEstacao(id, dto));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar estação", description = "Remove uma estação pelo ID")
    @APIResponse(responseCode = "204", description = "Estação removida com sucesso")
    @APIResponse(responseCode = "404", description = "Estação não encontrada")
    public void deletar(@RestPath long id) {
        estacaoService.deletarEstacao(id);
    }

    private EstacaoDTO mapDTO(Estacao estacao) {
        if (Objects.isNull(estacao))  {
            throw new NotFoundException("Estação não encontrada para o ID fornecido");
        }

        return EstacaoDTO.builder()
                .id(estacao.getId())
                .nome(estacao.getNome())
                .endereco(estacao.getEndereco())
                .telefone(estacao.getTelefone())
                .carro(estacao.getCarro())
                .dataInicio(estacao.getDataInicio() != null ? java.sql.Date.valueOf(estacao.getDataInicio()) : null)
                .dataCriacao(estacao.getDataCriacao() != null ? java.sql.Date.valueOf(estacao.getDataCriacao()) : null)
                .dataAtualizacao(estacao.getDataAtualizacao() != null ? java.sql.Date.valueOf(estacao.getDataAtualizacao()) : null)
                .build();
    }
}
