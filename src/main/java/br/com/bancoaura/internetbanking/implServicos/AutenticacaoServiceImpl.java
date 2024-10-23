package br.com.bancoaura.internetbanking.implServicos;

import br.com.bancoaura.internetbanking.dtos.ContaDto;
import br.com.bancoaura.internetbanking.entidades.Conta;
import br.com.bancoaura.internetbanking.mapeadores.MapeadorContaCliente;
import br.com.bancoaura.internetbanking.repositorios.ContaRepository;
import br.com.bancoaura.internetbanking.servicos.AutenticacaoService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static br.com.bancoaura.internetbanking.auxiliares.AuxiliarConstantes.*;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {
    @Autowired
    ContaRepository contaRepository;

    @Override
    public ContaDto autenticar(Integer idConta, String senha) {
        Optional<Conta> resultadoConta = contaRepository.findById(idConta);

        if (resultadoConta.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma conta encontrada com o número informado");
        }

        Conta conta = resultadoConta.get();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(senha, conta.getHashSenha())) {
            throw new IllegalArgumentException("Senha incorreta");
        }

        String token = JWT.create()
                .withIssuer(EMISSOR_TOKEN_JWT)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withSubject(conta.getId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + VALIDADE_TOKEN_JWT))
                .sign(Algorithm.HMAC256(SEGREDO_TOKEN_JWT.getBytes()));

        ContaDto contaDto = MapeadorContaCliente.entidadeParaDto(conta);

        contaDto.setToken(token);

        return contaDto;
    }
}
