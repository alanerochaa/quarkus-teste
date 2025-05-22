package com.rail.response.system.controller;

import com.rail.response.system.DTOs.MensagemDTO;
import com.rail.response.system.exception.NotFoundException;
import com.rail.response.system.model.Mensagem;
import com.rail.response.system.service.MensagemService;
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

@Path("mensagem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Mensagem", description = "Operações relacionadas à mensagem")
public class MensagemController {

    @Inject
    MensagemService mensagemService;

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar mensagem por ID", description = "Retorna uma mensagem pelo seu identificador")
    @APIResponse(responseCode = "200", description = "Mensagem encontrada")
    @APIResponse(responseCode = "404", description = "Mensagem não encontrada")
    public MensagemDTO getMensagem(@RestPath long id) {
        var mensagem = mensagemService.buscarMensagemPeloId(id);
        return mapMensagem(mensagem);
    }

    @GET
    @Operation(summary = "Listar mensagens", description = "Retorna a lista de todas as mensagens")
    @APIResponse(responseCode = "200", description = "Lista de mensagens")
    public List<MensagemDTO> listarMensagens() {
        return mensagemService.listarMensagens().stream()
                .map(this::mapMensagem)
                .collect(Collectors.toList());
    }

    @POST
    @Operation(summary = "Cadastrar mensagem", description = "Cadastra uma nova mensagem")
    @APIResponse(responseCode = "200", description = "Mensagem cadastrada com sucesso")
    public MensagemDTO cadastrarMensagem(MensagemDTO mensagemDTO) {
        ValidatorUtils.validate(mensagemDTO);

        var mensagem = mensagemService.cadastrarMensagem(mensagemDTO);
        return mapMensagem(mensagem);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar mensagem", description = "Atualiza uma mensagem existente")
    @APIResponse(responseCode = "200", description = "Mensagem atualizada com sucesso")
    @APIResponse(responseCode = "404", description = "Mensagem não encontrada")
    public MensagemDTO atualizarMensagem(@RestPath long id, MensagemDTO mensagemDTO) {
        ValidatorUtils.validate(mensagemDTO);

        var mensagem = mensagemService.alterarMensagem(id, mensagemDTO);
        return mapMensagem(mensagem);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletar mensagem", description = "Remove uma mensagem pelo ID")
    @APIResponse(responseCode = "204", description = "Mensagem removida com sucesso")
    @APIResponse(responseCode = "404", description = "Mensagem não encontrada")
    public void deletarMensagem(@RestPath long id) {
        mensagemService.deletarMensagem(id);
    }

    private MensagemDTO mapMensagem(Mensagem mensagem) {
        if (Objects.isNull(mensagem))  {
            throw new NotFoundException("Mensagem não encontrada para o ID fornecido");
        }

        return MensagemDTO.builder()
                .id(mensagem.getId())
                .conteudo(mensagem.getConteudo())
                .contato(mensagem.getContato())
                .dataCriacao(mensagem.getDataCriacao())
                .dataAtualizacao(mensagem.getDataAtualizacao())
                .idUsuario(mensagem.getUsuario().getIdUsuario())
                .build();
    }

}
