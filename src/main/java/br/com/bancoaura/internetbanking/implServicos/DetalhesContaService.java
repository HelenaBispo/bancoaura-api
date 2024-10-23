package br.com.bancoaura.internetbanking.implServicos;

import br.com.bancoaura.internetbanking.auxiliares.CustomUserDetails;
import br.com.bancoaura.internetbanking.entidades.Conta;
import br.com.bancoaura.internetbanking.repositorios.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetalhesContaService implements UserDetailsService {

    @Autowired
    private ContaRepository contaRepository;

    @Override
    public UserDetails loadUserByUsername(String idConta) throws UsernameNotFoundException {
        Optional<Conta> resultadoConta = contaRepository.findById(Integer.parseInt(idConta));

        if (resultadoConta.isEmpty()) {
            throw new UsernameNotFoundException(idConta);
        }

        return new CustomUserDetails(resultadoConta.get());
    }
}
