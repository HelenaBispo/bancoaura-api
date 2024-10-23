package br.com.bancoaura.internetbanking.implServicos;

import br.com.bancoaura.internetbanking.dtos.ClienteDto;
import br.com.bancoaura.internetbanking.dtos.ContaDto;
import br.com.bancoaura.internetbanking.servicos.AutenticacaoService;
import br.com.bancoaura.internetbanking.servicos.ContaClienteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AutenticacaoServiceImplTest {

    @Autowired
    ContaClienteService contaClienteService;

    @Autowired
    AutenticacaoService autenticacaoService;

    ContaDto contaDto;

    @BeforeEach
    void setUp() {
            List<String> telefones = Arrays.asList("83999999999", "84988888888");
            ClienteDto clienteDto = new ClienteDto()
                    .setCpf("23194509047")
                    .setNome("João Silva")
                    .setTelefones(telefones)
                    .setEmail("joaosilva@gmail.com")
                    .setSenha("12341234");
    
            contaDto = contaClienteService.criarContaCliente(clienteDto);
    }

    @Test
    @DisplayName("Deve retornar um token caso o usuário autentique com sucesso")
    @Transactional
    void testaRetornarTokenAutenticacaoSucesso() {
        ContaDto conta = autenticacaoService.autenticar(contaDto.getId(), "12341234");

        assertNotNull(conta);
        assertNotNull(conta.getToken());
    }

    @Test
    @DisplayName("Deve lançar exceção caso a senha esteja incorreta")
    void testaLancarExcecaoSenhaInvalida() {
        assertThrows(
            IllegalArgumentException.class,
            () -> autenticacaoService.autenticar(contaDto.getId(), "22222222")
        );
    }

    @Test
    @DisplayName("Deve lançar exceção caso a conta não exista")
    @Transactional
    void testaLancarExcecaoContaNaoExista() {
        assertThrows(
            EntityNotFoundException.class,
            () -> autenticacaoService.autenticar(5, "12341234")
        );
    }

    @AfterEach
    void tearDown() {
        contaClienteService.deletarContaCliente(contaDto.getId());
    }
}