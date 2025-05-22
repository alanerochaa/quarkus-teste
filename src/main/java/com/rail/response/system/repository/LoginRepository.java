package com.rail.response.system.repository;

import com.rail.response.system.model.Login;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoginRepository implements PanacheRepository<Login> {

    public Login buscarPeloEmail(String email) {
        return find("email = ?1", email).firstResult();
    }

    public boolean existeEmail(String email) {
        return find("email = ?1", email).count() > 0;
    }
}
