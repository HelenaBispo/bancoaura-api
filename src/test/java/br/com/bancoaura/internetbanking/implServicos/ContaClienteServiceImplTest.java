package br.com.bancoaura.internetbanking.implServicos;

import br.com.bancoaura.internetbanking.dtos.ClienteDto;
import br.com.bancoaura.internetbanking.dtos.ContaDto;
import br.com.bancoaura.internetbanking.dtos.DepositoDto;
import br.com.bancoaura.internetbanking.repositorios.ClienteRepository;
import br.com.bancoaura.internetbanking.repositorios.ContaRepository;
import br.com.bancoaura.internetbanking.servicos.ContaClienteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContaClienteServiceImplTest {

    @Autowired
    ContaClienteService contaClienteService;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Test
    @DisplayName("Deve criar uma nova conta a partir dos dados do cliente")
    void testaCriarConta() {
        List<String> telefones = Arrays.asList("83999999999", "84988888888");
        ClienteDto clienteDto = new ClienteDto()
                                    .setCpf("23194509047")
                                    .setNome("João Silva")
                                    .setTelefones(telefones)
                                    .setEmail("joaosilva@gmail.com")
                                    .setSenha("12341234");

        ContaDto contaCriada = contaClienteService.criarContaCliente(clienteDto);

        assertEquals(contaCriada.getCpfTitular(), "23194509047");
    }

    @Test
    @DisplayName("Deve lançar exceção caso um CPF inválido seja informado")
    void testaLancarExcecaoCpfInvalido() {
        List<String> telefones = Arrays.asList("83999999999", "84988888888");
        ClienteDto clienteDto = new ClienteDto()
                                    .setCpf("11122233344")
                                    .setNome("João Silva")
                                    .setTelefones(telefones)
                                    .setEmail("joaosilva@gmail.com")
                                    .setSenha("12341234");

        assertThrows(IllegalArgumentException.class, () -> contaClienteService.criarContaCliente(clienteDto));
    }

    @Test
    @DisplayName("Deve lançar exceção caso nenhum telefone seja informado")
    void testaLancarExcecaoTelefoneInvalido() {
        ClienteDto clienteDto = new ClienteDto()
                .setCpf("23194509047")
                .setNome("João Silva")
                .setEmail("joaosilva@gmail.com")
                .setSenha("12341234");

        assertThrows(IllegalArgumentException.class, () -> contaClienteService.criarContaCliente(clienteDto));
    }

    @Test
    @DisplayName("Deve lançar exceção caso nenhum e-mail seja informado")
    void testaLancarExcecaoEmailInvalido() {
        List<String> telefones = Arrays.asList("83999999999", "84988888888");
        ClienteDto clienteDto = new ClienteDto()
                .setCpf("23194509047")
                .setNome("João Silva")
                .setTelefones(telefones)
                .setSenha("12341234");

        assertThrows(DataIntegrityViolationException.class, () -> contaClienteService.criarContaCliente(clienteDto));
    }

    @Test
    @DisplayName("Deve lançar exceção caso nenhuma senha seja informada")
    void testaLancarExcecaoSenhaInvalida() {
        List<String> telefones = Arrays.asList("83999999999", "84988888888");
        ClienteDto clienteDto = new ClienteDto()
                .setCpf("23194509047")
                .setNome("João Silva")
                .setTelefones(telefones)
                .setEmail("joaosilva@gmail.com");

        assertThrows(IllegalArgumentException.class, () -> contaClienteService.criarContaCliente(clienteDto));
    }

    @Test
    @DisplayName("Deve retornar uma conta já criada")
    @Transactional()
    void testaRetornarContaJaCriada() {
        List<String> telefones = Arrays.asList("83999999999", "84988888888");
        ClienteDto clienteDto = new ClienteDto()
                .setCpf("23194509047")
                .setNome("João Silva")
                .setTelefones(telefones)
                .setEmail("joaosilva@gmail.com")
                .setSenha("12341234");

        ContaDto contaCriada = contaClienteService.criarContaCliente(clienteDto);

        ContaDto conta = contaClienteService.obterContaCliente(contaCriada.getId());

        assertEquals(contaCriada.getId(), conta.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao consultar uma conta inexistente")
    void testaRetornarContaInexistente() {
        assertThrows(EntityNotFoundException.class, () -> contaClienteService.obterContaCliente(1));
    }

    @Test
    @DisplayName("Deve alterar o valor do saldo em conta ao depositar valor")
    @Transactional
    void testaAlterarValorDoSaldoEmConta() {
        List<String> telefones = Arrays.asList("83999999999", "84988888888");
        ClienteDto clienteDto = new ClienteDto()
                .setCpf("23194509047")
                .setNome("João Silva")
                .setTelefones(telefones)
                .setEmail("joaosilva@gmail.com")
                .setSenha("12341234");

        ContaDto contaCriada = contaClienteService.criarContaCliente(clienteDto);

        DepositoDto depositoDto = new DepositoDto()
                                    .setContaBeneficiario(contaCriada.getId())
                                    .setValor(new BigDecimal("100"));

        ContaDto contaNovoSaldo = contaClienteService.depositarContaCliente(depositoDto);

        assertEquals(contaCriada.getSaldo(), BigDecimal.ZERO);
        assertEquals(contaNovoSaldo.getSaldo(), contaCriada.getSaldo().add(depositoDto.getValor()));
    }

    @Test
    @DisplayName("Deve lançar exceção ao fazer depósito de valor menor ou igual a zero")
    void testaLancarExcecaoValorDepositoInvalido() {
        DepositoDto depositoDto = new DepositoDto().setContaBeneficiario(1).setValor(BigDecimal.ZERO);

        assertThrows(
            IllegalArgumentException.class,
            () -> contaClienteService.depositarContaCliente(depositoDto)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção ao fazer depósito em conta inexistente")
    void testaLancarExcecaoContaInexistente() {
        DepositoDto depositoDto = new DepositoDto().setContaBeneficiario(1).setValor(new BigDecimal("1000"));

        assertThrows(
            EntityNotFoundException.class,
            () -> contaClienteService.depositarContaCliente(depositoDto)
        );
    }

    @Test
    @DisplayName("Deve deletar o cliente e sua conta")
    void testaDeletarCliente() {
        List<String> telefones = Arrays.asList("83999999999", "84988888888");
        ClienteDto clienteDto = new ClienteDto()
                .setCpf("23194509047")
                .setNome("João Silva")
                .setTelefones(telefones)
                .setEmail("joaosilva@gmail.com")
                .setSenha("12341234");

        ContaDto contaCriada = contaClienteService.criarContaCliente(clienteDto);

        contaClienteService.deletarContaCliente(contaCriada.getId());

        assertThrows(
            EntityNotFoundException.class,
            () -> contaClienteService.obterContaCliente(contaCriada.getId())
        );
    }

    @AfterEach
    void tearDown() {
        contaRepository.deleteAll();
        clienteRepository.deleteAll();
    }
}