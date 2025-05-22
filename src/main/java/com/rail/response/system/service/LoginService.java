package com.rail.response.system.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.rail.response.system.model.Login;
import com.rail.response.system.repository.LoginRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class LoginService {

    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

    @Inject
    LoginRepository loginRepository;

    public boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean existeEmail(String email) {
        return loginRepository.existeEmail(email);
    }

    @Transactional
    public void cadastrarLogin(Login login) {
        if (existeEmail(login.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        String hashSenha = BCrypt.withDefaults().hashToString(12, login.getSenha().toCharArray());
        login.setSenha(hashSenha);
        loginRepository.persist(login);
    }

    public Login buscarPorId(Long id) {
        return loginRepository.findById(id);
    }

    public Login buscarPeloEmail(String email) {
        return loginRepository.buscarPeloEmail(email);
    }

    public Login autenticarLogin(String email, String senha) {
        Login login = buscarPeloEmail(email);
        if (login == null) return null;

        boolean senhaValida = BCrypt.verifyer().verify(senha.toCharArray(), login.getSenha()).verified;

        return senhaValida ? login : null;
    }

    @Transactional
    public boolean mudarSenha(Login login, String novaSenha) {
        LOGGER.info("Tentando mudar senha do usuário: " + login.getEmail());

        LOGGER.info("Verificando se nova senha é igual à senha antiga...");
        if (BCrypt.verifyer().verify(novaSenha.toCharArray(), login.getSenha()).verified) {
            LOGGER.warning("Nova senha igual à senha antiga.");
            return false; // Senha nova igual à antiga
        }

        LOGGER.info("Validando nova senha...");
        if (!validarSenha(novaSenha)) {
            LOGGER.warning("Nova senha não atende aos critérios de segurança.");
            return false; // Senha nova inválida
        }

        String hashNovaSenha = BCrypt.withDefaults().hashToString(12, novaSenha.toCharArray());

        var loginToUpdate = loginRepository.buscarPeloEmail(login.getEmail());
        loginToUpdate.setSenha(hashNovaSenha);

        loginRepository.persist(loginToUpdate);

        LOGGER.info("Senha alterada com sucesso para o usuário: " + login.getEmail());
        return true;
    }

    public boolean verificarSenha(String senha) {
        return validarSenha(senha);
    }

    private boolean validarSenha(String senha) {
        if (senha == null || senha.length() < 6) return false;
        if (!senha.matches(".*[A-Za-z].*")) return false;
        return senha.matches(".*\\d.*");
    }

    public List<Login> listarTodos() {
        return loginRepository.listAll();
    }
}
