package br.com.bancoaura.internetbanking.auxiliares;

import br.com.bancoaura.internetbanking.entidades.Conta;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private Conta conta;

    public CustomUserDetails(Conta conta) {
        this.conta = conta;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return conta.getHashSenha();
    }

    @Override
    public String getUsername() {
        return conta.getId().toString();
    }
}
