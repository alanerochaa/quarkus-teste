package com.rail.response.system.controller;

import com.rail.response.system.DTOs.LoginDTO;
import com.rail.response.system.DTOs.ResponseDTO;
import com.rail.response.system.model.Login;
import com.rail.response.system.service.LoginService;
import com.rail.response.system.util.ValidatorUtils;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Login", description = "Operações de autenticação")
public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @Inject
    LoginService loginService;

    @GET
    @Operation(summary = "Listar todos os logins", description = "Retorna todos os logins cadastrados, sem expor a senha")
    @APIResponse(responseCode = "200", description = "Lista de logins")
    public Response listarTodos() {
        List<Login> logins = loginService.listarTodos();

        List<LoginDTO> loginsDTO = logins.stream()
                .map(login -> LoginDTO.builder()
                        .idLogin(login.getIdLogin())
                        .email(login.getEmail())
                        .status(login.getStatus())
                        .build())
                .collect(Collectors.toList());

        return Response.ok(loginsDTO).build();
    }

    @POST
    @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna um token de acesso")
    @APIResponse(responseCode = "200", description = "Login realizado com sucesso")
    @APIResponse(responseCode = "401", description = "Credenciais inválidas")
    public Response login(@RequestBody LoginDTO loginDTO) {
        ValidatorUtils.validate(loginDTO);
        var login = loginService.autenticarLogin(loginDTO.getEmail(), loginDTO.getSenha());
        if (login == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Collections.singletonMap("erro", "Credenciais inválidas"))
                    .build();
        }

        return Response.status(Response.Status.OK)
                .entity(ResponseDTO.builder()
                        .message("Operação realizada com sucesso.")
                        .status(Response.Status.OK).build())
                .build();
    }

    @POST
    @Path("/cadastrar")
    @Operation(summary = "Cadastrar novo login", description = "Cadastra um novo login de usuário")
    @APIResponse(responseCode = "201", description = "Login cadastrado com sucesso")
    @APIResponse(responseCode = "400", description = "Email inválido")
    @APIResponse(responseCode = "409", description = "Email já cadastrado")
    public Response cadastrar(@RequestBody LoginDTO loginDTO) {
        ValidatorUtils.validate(loginDTO);

        if (!loginService.validarEmail(loginDTO.getEmail())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Email inválido").build();
        }

        if (loginService.existeEmail(loginDTO.getEmail())) {
            return Response.status(Response.Status.CONFLICT).entity("Email já cadastrado").build();
        }

        Login novoLogin = Login.builder()
                .email(loginDTO.getEmail())
                .senha(loginDTO.getSenha())
                .status("ativo")
                .build();

        loginService.cadastrarLogin(novoLogin);
        return Response.status(Response.Status.CREATED)
                .entity(ResponseDTO.builder()
                        .message("Login cadastrado com sucesso")
                        .status(Response.Status.CREATED).build())
                .build();
    }

    @PUT
    @Path("/alterar-senha")
    @Operation(summary = "Alterar senha", description = "Altera a senha de um login existente")
    @APIResponse(responseCode = "200", description = "Senha alterada com sucesso")
    @APIResponse(responseCode = "400", description = "Senha inválida ou igual à anterior")
    @APIResponse(responseCode = "401", description = "Credenciais inválidas")
    public Response alterarSenha(@RequestBody LoginDTO loginDTO, @QueryParam("novaSenha") String novaSenha) {
        ValidatorUtils.validate(loginDTO);

        LOGGER.info("Recebido pedido para alterar senha do usuário: " + loginDTO.getEmail());

        var login = loginService.autenticarLogin(loginDTO.getEmail(), loginDTO.getSenha());

        if (login == null) {
            LOGGER.warning("Falha na autenticação para usuário: " + loginDTO.getEmail());
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(ResponseDTO.builder()
                            .message("Credenciais inválidas")
                            .status(Response.Status.UNAUTHORIZED).build())
                    .build();
        }

        boolean sucesso = loginService.mudarSenha(login, novaSenha);
        if (sucesso) {
            LOGGER.info("Senha alterada com sucesso para usuário: " + loginDTO.getEmail());
            return Response.status(Response.Status.OK)
                    .entity(ResponseDTO.builder()
                            .message("Senha alterada com sucesso")
                            .status(Response.Status.OK).build())
                    .build();
        }

        LOGGER.warning("Falha ao alterar senha para usuário: " + loginDTO.getEmail());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ResponseDTO.builder()
                        .message("Senha inválida ou igual à anterior")
                        .status(Response.Status.BAD_REQUEST).build())
                .build();
    }

    @GET
    @Path("/verificar-senha")
    @Operation(summary = "Verificar força da senha", description = "Verifica se a senha atende aos critérios de segurança")
    @APIResponse(responseCode = "200", description = "Resultado da verificação")
    public Response verificarSenha(@QueryParam("senha") String senha) {
        boolean valida = loginService.verificarSenha(senha);
        return Response.status(Response.Status.OK)
                .entity(ResponseDTO.builder()
                        .isValid(valida)
                        .status(Response.Status.OK).build())
                .build();
    }
}
