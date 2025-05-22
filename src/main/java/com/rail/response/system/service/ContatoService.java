package com.rail.response.system.service;

import com.rail.response.system.DTOs.ContatoDTO;
import com.rail.response.system.model.Contato;
import com.rail.response.system.model.Usuario;
import com.rail.response.system.repository.ContatoRepository;
import com.rail.response.system.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ContatoService {

    @Inject
    ContatoRepository contatoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    public List<Contato> listarContatos() {
        return contatoRepository.listAll();
    }

    public Contato buscarContatoPeloId(Long id) {
        return contatoRepository.findById(id);
    }

    @Transactional
    public Contato cadastrarContato(ContatoDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        Contato contato = Contato.builder()
                .valorContato(dto.getValorContato())
                .tipoContato(dto.getTipoContato())
                .usuario(usuario)
                .build();


        contatoRepository.persist(contato);
        return contato;
    }

    @Transactional
    public Contato atualizarContato(Long id, ContatoDTO dto) {
        Contato contato = contatoRepository.findById(id);
        if (contato == null) return null;

        contato.setValorContato(dto.getValorContato());
        contato.setTipoContato(dto.getTipoContato());
        contato.setDataAtualizacao(LocalDate.now());

        return contato;
    }

    @Transactional
    public void deletarContato(Long id) {
        contatoRepository.deleteById(id);
    }
}
