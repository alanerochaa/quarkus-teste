package com.rail.response.system.service;

import com.rail.response.system.DTOs.UsuarioDTO;
import com.rail.response.system.model.Login;
import com.rail.response.system.model.Usuario;
import com.rail.response.system.repository.UsuarioRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    EntityManager entityManager;

    // Buscar usuário por ID com validação
    public Usuario buscarUsuarioPeloId(long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new RuntimeException("Usuário com ID " + id + " não encontrado.");
        }
        return usuario;
    }

    // Verifica se o usuário é administrador
    public boolean usuarioEhAdmin(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            throw new RuntimeException("Usuário com ID " + idUsuario + " não encontrado.");
        }
        return usuario.isAdministrador();
    }

    // Cadastrar um novo usuário
    @Transactional
    public Usuario cadastrarUsuario(UsuarioDTO usuarioDTO) {
        var usuarioParaSalvar = mapUsuario(usuarioDTO);
        usuarioParaSalvar.setDataCriacao(LocalDate.now());

        usuarioRepository.persist(usuarioParaSalvar);

        return usuarioParaSalvar;
    }

    // Alterar um usuário existente
    @Transactional
    public Usuario alterarUsuario(long idUsuario, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            throw new RuntimeException("Usuário com ID " + idUsuario + " não encontrado.");
        }

        usuario.setNome(usuarioDTO.getNome());
        return usuario;
    }

    // Deletar um usuário pelo ID
    @Transactional
    public void deletarUsuario(Long id) {
        boolean deletado = usuarioRepository.deleteById(id);
        if (!deletado) {
            throw new RuntimeException("Usuário com ID " + id + " não encontrado para exclusão.");
        }
    }

    // Listar todos os usuários ordenados por ID
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listAll(Sort.by("id"));
    }

    // Mapeamento de DTO para entidade
    private Usuario mapUsuario(UsuarioDTO usuarioDTO) {
        String adminFlag = usuarioDTO.isAdministrador() ? "S" : "N";

        Login login = entityManager.find(Login.class, usuarioDTO.getIdLogin());
        if (login == null) {
            throw new RuntimeException("Login com ID " + usuarioDTO.getIdLogin() + " não encontrado.");
        }

        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .adminFlag(adminFlag)
                .nivelUsuario(usuarioDTO.getNivelUsuario())
                .nivelInterno(usuarioDTO.getNivelInterno())
                .login(login)
                .build();
    }
}
