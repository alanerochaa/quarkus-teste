package com.rail.response.system.repository;

import com.rail.response.system.model.Notificacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotificacaoRepository implements PanacheRepository<Notificacao> {
}
