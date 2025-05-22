package com.rail.response.system.controller;

import com.rail.response.system.DTOs.ContatoDTO;
import com.rail.response.system.exception.NotFoundException;
import com.rail.response.system.model.Contato;
import com.rail.response.system.service.ContatoService;
import com.rail.response.system.util.ValidatorUtils;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestPath;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Path("contato")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Contato", description = "Operações relacionadas aos contatos")
public class ContatoController {

    @Inject
    ContatoService contatoService;

    @GET
    @Operation(summary = "Lista todos os contatos", description = "Retorna uma lista de todos os contatos cadastrados.")
    @APIResponse(responseCode = "200", description = "Lista de contatos retornada com sucesso")
    public List<ContatoDTO> listarContatos() {
        return contatoService.listarContatos().stream()
                .map(this::mapContato)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Busca um contato pelo ID", description = "Retorna os dados do contato correspondente ao ID informado.")
    @APIResponse(responseCode = "200", description = "Contato encontrada")
    @APIResponse(responseCode = "404", description = "Contato não encontrada")
    public ContatoDTO buscarPorId(
        @Parameter(description = "ID do contato", required = true)
        @RestPath Long id) {
        return mapContato(contatoService.buscarContatoPeloId(id));
    }

    @POST
    @Operation(summary = "Cadastra um novo contato", description = "Cria um novo contato com os dados informados.")
    @APIResponse(responseCode = "201", description = "Contato criado com sucesso")
    public ContatoDTO cadastrar(ContatoDTO dto) {
        ValidatorUtils.validate(dto);
        return mapContato(contatoService.cadastrarContato(dto));
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualiza um contato", description = "Atualiza os dados do contato correspondente ao ID informado.")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Contato atualizado com sucesso"),
        @APIResponse(responseCode = "404", description = "Contato não encontrado")
    })
    public ContatoDTO atualizar(
        @Parameter(description = "ID do contato", required = true)
        @RestPath Long id,
        ContatoDTO dto) {
        ValidatorUtils.validate(dto);
        return mapContato(contatoService.atualizarContato(id, dto));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Remove um contato", description = "Remove o contato correspondente ao ID informado.")
    @APIResponses({
        @APIResponse(responseCode = "204", description = "Contato removido com sucesso"),
        @APIResponse(responseCode = "404", description = "Contato não encontrado")
    })
    public void deletar(
        @Parameter(description = "ID do contato", required = true)
        @RestPath Long id) {
        contatoService.deletarContato(id);
    }

    private ContatoDTO mapContato(Contato contato) {
        if (Objects.isNull(contato))  {
            throw new NotFoundException("Contato não encontrada para o ID fornecido");
        }

        return ContatoDTO.builder()
                .id(contato.getId())
                .valorContato(contato.getValorContato())
                .tipoContato(contato.getTipoContato())
                .dataCriacao(convertLocalDateToDate(contato.getDataCriacao()))
                .dataAtualizacao(convertLocalDateToDate(contato.getDataAtualizacao()))
                .idUsuario(contato.getUsuario() != null && contato.getUsuario().getIdUsuario() != null
                        ? contato.getUsuario().getIdUsuario().longValue()
                        : null)
                .build();
    }

    private Date convertLocalDateToDate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
