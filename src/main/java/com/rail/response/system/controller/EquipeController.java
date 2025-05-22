package com.rail.response.system.controller;

import com.rail.response.system.DTOs.EquipeDTO;
import com.rail.response.system.exception.NotFoundException;
import com.rail.response.system.model.Equipe;
import com.rail.response.system.service.EquipeService;
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

@Path("equipe")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Equipe", description = "Operações relacionadas à equipe")
public class EquipeController {

    @Inject
    EquipeService equipeService;

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar equipe por ID", description = "Retorna uma equipe pelo seu identificador")
    @APIResponse(responseCode = "200", description = "Equipe encontrada")
    @APIResponse(responseCode = "404", description = "Equipe não encontrada")
    public EquipeDTO get(@RestPath long id) {
        return mapEquipe(equipeService.buscarPorId(id));
    }

    @GET
    @Operation(summary = "Listar equipes", description = "Retorna a lista de todas as equipes")
    @APIResponse(responseCode = "200", description = "Lista de equipes")
    public List<EquipeDTO> listar() {
        return equipeService.listar().stream().map(this::mapEquipe).collect(Collectors.toList());
    }

    @POST
    @Operation(summary = "Cadastrar equipe", description = "Cadastra uma nova equipe")
    @APIResponse(responseCode = "201", description = "Equipe cadastrada com sucesso")
    public EquipeDTO cadastrar(EquipeDTO dto) {
        ValidatorUtils.validate(dto);
        return mapEquipe(equipeService.cadastrarEquipe(dto));
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar equipe", description = "Atualiza uma equipe existente")
    @APIResponse(responseCode = "200", description = "Equipe atualizada com sucesso")
    @APIResponse(responseCode = "404", description = "Equipe não encontrada")
    public EquipeDTO atualizar(@RestPath long id, EquipeDTO dto) {
        ValidatorUtils.validate(dto);
        return mapEquipe(equipeService.alterarEquipe(id, dto));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar equipe", description = "Remove uma equipe pelo ID")
    @APIResponse(responseCode = "204", description = "Equipe removida com sucesso")
    @APIResponse(responseCode = "404", description = "Equipe não encontrada")
    public void deletar(@RestPath long id) {
        equipeService.deletarEquipe(id);
    }

    private EquipeDTO mapEquipe(Equipe equipe) {
        if (Objects.isNull(equipe))  {
            throw new NotFoundException("Equipe não encontrada para o ID fornecido");
        }

        return EquipeDTO.builder()
                .id(equipe.getId())
                .nomeEquipe(equipe.getNomeEquipe())
                .tipoExperiencia(equipe.getTipoExperiencia())
                .contatoResponsavel(equipe.getContatoResponsavel())
                .build();
    }
}
