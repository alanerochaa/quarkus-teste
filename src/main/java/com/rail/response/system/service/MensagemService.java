package com.rail.response.system.service;

import com.rail.response.system.DTOs.MensagemDTO;
import com.rail.response.system.model.Mensagem;
import com.rail.response.system.model.Usuario;
import com.rail.response.system.repository.MensagemRepository;
import com.rail.response.system.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class MensagemService {

    @Inject
    MensagemRepository mensagemRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    public Mensagem buscarMensagemPeloId(long id) {
        return mensagemRepository.findById(id);
    }

    public List<Mensagem> listarMensagens() {
        return mensagemRepository.listAll();
    }

    @Transactional
    public Mensagem cadastrarMensagem(MensagemDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario().longValue());
        Mensagem mensagem = Mensagem.builder()
                .conteudo(dto.getConteudo())
                .contato(dto.getContato())
                .usuario(usuario)
                .build();

        mensagemRepository.persist(mensagem);
        return mensagem;
    }


    @Transactional
    public Mensagem alterarMensagem(long id, MensagemDTO dto) {
        Mensagem mensagem = mensagemRepository.findById(id);
        if (mensagem == null) return null;

        mensagem.setConteudo(dto.getConteudo());
        mensagem.setContato(dto.getContato());
        mensagem.setDataAtualizacao(LocalDate.now());

        return mensagem;
    }


    @Transactional
    public void deletarMensagem(long id) {
        mensagemRepository.deleteById(id);
    }
}
