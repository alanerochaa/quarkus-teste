package com.rail.response.system.controller;

import com.rail.response.system.DTOs.UsuarioDTO;
import com.rail.response.system.exception.NotFoundException;
import com.rail.response.system.model.Usuario;
import com.rail.response.system.service.UsuarioService;
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

@Path("usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Usuario", description = "Operações relacionadas ao usuário")
public class UsuarioController {

    @Inject
    UsuarioService usuarioService;

    @Path("/{idUsuario}")
    @GET
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário pelo seu identificador")
    @APIResponse(responseCode = "200", description = "Usuário encontrado")
    @APIResponse(responseCode = "404", description = "Usuário não encontrado")
    public UsuarioDTO getUsuario(@RestPath long idUsuario) {
        var usuario = usuarioService.buscarUsuarioPeloId(idUsuario);
        return mapUsuario(usuario);
    }

    @GET
    @Operation(summary = "Listar usuários", description = "Retorna a lista de todos os usuários")
    @APIResponse(responseCode = "200", description = "Lista de usuários")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios().stream().map(this::mapUsuario).collect(Collectors.toList());
    }

    @Path("/{idUsuario}")
    @PUT
    @Operation(summary = "Atualizar usuário", description = "Atualiza um usuário existente")
    @APIResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @APIResponse(responseCode = "404", description = "Usuário não encontrado")
    public UsuarioDTO atualizarUsuario(@RestPath long idUsuario, UsuarioDTO usuarioDTO) {
        ValidatorUtils.validate(usuarioDTO);

        var usuario = usuarioService.alterarUsuario(idUsuario, usuarioDTO);
        return mapUsuario(usuario);
    }

    @POST
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário")
    @APIResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) {
        ValidatorUtils.validate(usuarioDTO);

        var usuario = usuarioService.cadastrarUsuario(usuarioDTO);
        return mapUsuario(usuario);
    }

    @Path("/{idUsuario}")
    @DELETE
    @Operation(summary = "Deletar usuário", description = "Remove um usuário pelo ID")
    @APIResponse(responseCode = "204", description = "Usuário removido com sucesso")
    @APIResponse(responseCode = "404", description = "Usuário não encontrado")
    public void delete(@RestPath long idUsuario) {
        usuarioService.deletarUsuario(idUsuario);
    }

    private UsuarioDTO mapUsuario(Usuario usuario) {
        if (Objects.isNull(usuario))  {
            throw new NotFoundException("Usuario não encontrada para o ID fornecido");
        }

        var usuarioEhAdmin = usuario.getAdminFlag().equalsIgnoreCase("S");
        return UsuarioDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nome(usuario.getNome())
                .administrador(usuarioEhAdmin)
                .build();
    }
}
