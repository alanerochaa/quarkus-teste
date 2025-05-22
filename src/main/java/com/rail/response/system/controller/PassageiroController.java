package com.rail.response.system.controller;

import com.rail.response.system.DTOs.PassageiroDTO;
import com.rail.response.system.exception.NotFoundException;
import com.rail.response.system.model.Passageiro;
import com.rail.response.system.service.PassageiroService;
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

@Path("passageiro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Passageiro", description = "Operações relacionadas ao passageiro")
public class PassageiroController {

    @Inject
    PassageiroService passageiroService;

    @Path("/{id}")
    @GET
    @Operation(summary = "Buscar passageiro por ID", description = "Retorna um passageiro pelo seu identificador")
    @APIResponse(responseCode = "200", description = "Passageiro encontrado")
    @APIResponse(responseCode = "404", description = "Passageiro não encontrado")
    public PassageiroDTO buscar(@RestPath long id) {
        var passageiro = passageiroService.buscarPorId(id);
        return mapToDTO(passageiro);
    }

    @GET
    @Operation(summary = "Listar passageiros", description = "Retorna a lista de todos os passageiros")
    @APIResponse(responseCode = "200", description = "Lista de passageiros")
    public List<PassageiroDTO> listarTodos() {
        return passageiroService.listarTodos().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @POST
    @Operation(summary = "Cadastrar passageiro", description = "Cadastra um novo passageiro")
    @APIResponse(responseCode = "201", description = "Passageiro cadastrado com sucesso")
    public PassageiroDTO cadastrar(PassageiroDTO dto) {
        ValidatorUtils.validate(dto);
        return mapToDTO(passageiroService.cadastrar(dto));
    }

    @Path("/{id}")
    @PUT
    @Operation(summary = "Atualizar passageiro", description = "Atualiza um passageiro existente")
    @APIResponse(responseCode = "200", description = "Passageiro atualizado com sucesso")
    @APIResponse(responseCode = "404", description = "Passageiro não encontrado")
    public PassageiroDTO atualizar(@RestPath long id, PassageiroDTO dto) {
        ValidatorUtils.validate(dto);
        return mapToDTO(passageiroService.atualizar(id, dto));
    }

    @Path("/{id}")
    @DELETE
    @Operation(summary = "Deletar passageiro", description = "Remove um passageiro pelo ID")
    @APIResponse(responseCode = "204", description = "Passageiro removido com sucesso")
    @APIResponse(responseCode = "404", description = "Passageiro não encontrado")
    public void deletar(@RestPath long id) {
        passageiroService.deletar(id);
    }

    private PassageiroDTO mapToDTO(Passageiro passageiro) {
        if (Objects.isNull(passageiro))  {
            throw new NotFoundException("Passageiro não encontrada para o ID fornecido");
        }

        return PassageiroDTO.builder()
                .id(passageiro.getId())
                .nome(passageiro.getNome())
                .documento(passageiro.getDocumento())
                .descricao(passageiro.getDescricao())
                .responsavel(passageiro.getResponsavel())
                .contato(passageiro.getContato())
                .dataCriacao(passageiro.getDataCriacao())
                .dataAtualizacao(passageiro.getDataAtualizacao())
                .build();
    }
}
