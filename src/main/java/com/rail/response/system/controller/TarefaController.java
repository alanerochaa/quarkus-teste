package com.rail.response.system.controller;

import com.rail.response.system.DTOs.TarefaDTO;
import com.rail.response.system.exception.NotFoundException;
import com.rail.response.system.model.Tarefa;
import com.rail.response.system.service.TarefaService;
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

@Path("tarefa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Tarefa", description = "Operações relacionadas à tarefa")
public class TarefaController {

    @Inject
    TarefaService tarefaService;

    @Path("/{idTarefa}")
    @GET
    @Operation(summary = "Buscar tarefa por ID", description = "Retorna uma tarefa pelo seu identificador")
    @APIResponse(responseCode = "200", description = "Tarefa encontrada")
    @APIResponse(responseCode = "404", description = "Tarefa não encontrada")
    public TarefaDTO buscarTarefa(@RestPath long idTarefa) {
        var tarefa = tarefaService.buscarTarefaPeloId(idTarefa);
        return mapTarefa(tarefa);
    }

    @GET
    @Operation(summary = "Listar tarefas", description = "Retorna a lista de todas as tarefas")
    @APIResponse(responseCode = "200", description = "Lista de tarefas")
    public List<TarefaDTO> listarTarefas() {
        return tarefaService.listarTarefas().stream().map(this::mapTarefa).collect(Collectors.toList());
    }

    @POST
    @Operation(summary = "Cadastrar tarefa", description = "Cadastra uma nova tarefa")
    @APIResponse(responseCode = "201", description = "Tarefa cadastrada com sucesso")
    public TarefaDTO cadastrarTarefa(TarefaDTO tarefaDTO) {
        ValidatorUtils.validate(tarefaDTO);

        var tarefa = tarefaService.cadastrarTarefa(tarefaDTO);
        return mapTarefa(tarefa);
    }

    @Path("/{idTarefa}")
    @PUT
    @Operation(summary = "Atualizar tarefa", description = "Atualiza uma tarefa existente")
    @APIResponse(responseCode = "200", description = "Tarefa atualizada com sucesso")
    @APIResponse(responseCode = "404", description = "Tarefa não encontrada")
    public TarefaDTO atualizarTarefa(@RestPath long idTarefa, TarefaDTO tarefaDTO) {
        ValidatorUtils.validate(tarefaDTO);

        var tarefa = tarefaService.alterarTarefa(idTarefa, tarefaDTO);
        return mapTarefa(tarefa);
    }

    @Path("/{idTarefa}")
    @DELETE
    @Operation(summary = "Deletar tarefa", description = "Remove uma tarefa pelo ID")
    @APIResponse(responseCode = "204", description = "Tarefa removida com sucesso")
    @APIResponse(responseCode = "404", description = "Tarefa não encontrada")
    public void deletarTarefa(@RestPath long idTarefa) {
        tarefaService.deletarTarefa(idTarefa);
    }

    private TarefaDTO mapTarefa(Tarefa tarefa) {
        if (Objects.isNull(tarefa))  {
            throw new NotFoundException("Tarefa não encontrada para o ID fornecido");
        }

        return TarefaDTO.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .status(tarefa.getStatus())
                .dataCriacao(tarefa.getDataCriacao())
                .dataAtualizacao(tarefa.getDataAtualizacao())
                .idUsuario(tarefa.getUsuario().getIdUsuario())
                .build();
    }
}
