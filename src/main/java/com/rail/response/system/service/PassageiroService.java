package com.rail.response.system.service;

import com.rail.response.system.DTOs.PassageiroDTO;
import com.rail.response.system.model.Passageiro;
import com.rail.response.system.repository.PassageiroRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class PassageiroService {

    @Inject
    PassageiroRepository passageiroRepository;

    public Passageiro buscarPorId(long id) {
        var passageiro = passageiroRepository.findById(id);
        if (passageiro == null) {
            throw new WebApplicationException("Passageiro não encontrado", Response.Status.NOT_FOUND);
        }
        return passageiro;
    }

    public List<Passageiro> listarTodos() {
        return passageiroRepository.listAll();
    }

    @Transactional
    public Passageiro cadastrar(PassageiroDTO dto) {
        if (documentoExiste(dto.getDocumento())) {
            throw new WebApplicationException("Documento já cadastrado", Response.Status.CONFLICT);
        }

        Passageiro passageiro = mapToEntity(dto);
        passageiroRepository.persist(passageiro);
        return passageiro;
    }

    @Transactional
    public Passageiro atualizar(long id, PassageiroDTO dto) {
        Passageiro passageiro = passageiroRepository.findById(id);
        if (passageiro == null) {
            throw new WebApplicationException("Passageiro não encontrado", Response.Status.NOT_FOUND);
        }

        // Verifica se o novo documento é diferente e já existe para outro passageiro
        if (dto.getDocumento() != null && !dto.getDocumento().equals(passageiro.getDocumento()) && documentoExiste(dto.getDocumento())) {
            throw new WebApplicationException("Documento já em uso por outro passageiro", Response.Status.CONFLICT);
        }

        if (dto.getNome() != null) passageiro.setNome(dto.getNome());
        if (dto.getDocumento() != null) passageiro.setDocumento(dto.getDocumento());
        if (dto.getDescricao() != null) passageiro.setDescricao(dto.getDescricao());
        if (dto.getResponsavel() != null) passageiro.setResponsavel(dto.getResponsavel());
        if (dto.getContato() != null) passageiro.setContato(dto.getContato());

        passageiro.setDataAtualizacao(LocalDate.now());
        return passageiro;
    }

    @Transactional
    public void deletar(long id) {
        boolean deleted = passageiroRepository.deleteById(id);
        if (!deleted) {
            throw new WebApplicationException("Passageiro não encontrado para exclusão", Response.Status.NOT_FOUND);
        }
    }

    private boolean documentoExiste(String documento) {
        return passageiroRepository.find("documento", documento).firstResultOptional().isPresent();
    }

    private Passageiro mapToEntity(PassageiroDTO dto) {
        return Passageiro.builder()
                .nome(dto.getNome())
                .documento(dto.getDocumento())
                .descricao(dto.getDescricao())
                .responsavel(dto.getResponsavel())
                .contato(dto.getContato())
                .build();
    }
}
