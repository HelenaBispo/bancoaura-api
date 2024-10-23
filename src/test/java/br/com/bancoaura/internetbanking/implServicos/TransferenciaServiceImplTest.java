package br.com.bancoaura.internetbanking.implServicos;

import br.com.bancoaura.internetbanking.dtos.ClienteDto;
import br.com.bancoaura.internetbanking.dtos.ContaDto;
import br.com.bancoaura.internetbanking.dtos.DepositoDto;
import br.com.bancoaura.internetbanking.dtos.TransferenciaDto;
import br.com.bancoaura.internetbanking.servicos.ContaClienteService;
import br.com.bancoaura.internetbanking.servicos.TransferenciaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransferenciaServiceImplTest {

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    ContaClienteService contaClienteService;

    ContaDto pagante, beneficiario;

    @BeforeEach
    void setUp() {
        List<String> telefones = Arrays.asList("83999999999", "84988888888");
        ClienteDto paganteDto = new ClienteDto()
                .setCpf("23194509047")
                .setNome("João Silva")
                .setTelefones(telefones)
                .setEmail("joaosilva@gmail.com")
                .setSenha("12341234");

        ClienteDto beneficiarioDto = new ClienteDto()
                .setCpf("06927635033")
                .setNome("Maria Silva")
                .setTelefones(telefones)
                .setEmail("mariasilva@gmail.com")
                .setSenha("12341234");

        pagante = contaClienteService.criarContaCliente(paganteDto);
        beneficiario = contaClienteService.criarContaCliente(beneficiarioDto);

        DepositoDto depositoDto = new DepositoDto()
                                    .setContaBeneficiario(pagante.getId())
                                    .setValor(new BigDecimal("100.00"));

        pagante = contaClienteService.depositarContaCliente(depositoDto);
    }

    @AfterEach
    void tearDown() {
        contaClienteService.deletarContaCliente(pagante.getId());
        contaClienteService.deletarContaCliente(beneficiario.getId());
    }

    @Test
    @DisplayName("Deve criar uma nova transferencia com os dados informados")
    @Transactional
    void criarTransferencia() {
        TransferenciaDto transferencia =
                new TransferenciaDto()
                        .setContaBeneficiario(beneficiario.getId())
                        .setContaPagante(pagante.getId())
                        .setValor(new BigDecimal("100.00"));

        TransferenciaDto transferenciaRealizada = transferenciaService.criarTransferencia(transferencia);

        assertEquals(pagante.getId(), transferenciaRealizada.getContaPagante());
        assertEquals(beneficiario.getId(), transferenciaRealizada.getContaBeneficiario());
        assertEquals(transferencia.getValor(), transferenciaRealizada.getValor());
        assertNotNull(transferenciaRealizada.getId());
        assertNotEquals("", transferenciaRealizada.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção se valor da transferência for menor que o saldo do pagante")
    @Transactional
    void testaLancarExcecaoValorMenorQueSaldoPagante() {
        TransferenciaDto transferencia =
                new TransferenciaDto()
                        .setContaBeneficiario(beneficiario.getId())
                        .setContaPagante(pagante.getId())
                        .setValor(new BigDecimal("101.00"));

        assertThrows(
            IllegalArgumentException.class,
            () -> transferenciaService.criarTransferencia(transferencia)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção caso conta do beneficiario não exista")
    @Transactional
    void testaLancarExcecaoContaBeneficiarioNaoExiste() {
        TransferenciaDto transferencia =
                new TransferenciaDto()
                        .setContaBeneficiario(5)
                        .setContaPagante(pagante.getId())
                        .setValor(new BigDecimal("101.00"));

        assertThrows(
                IllegalArgumentException.class,
                () -> transferenciaService.criarTransferencia(transferencia)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção caso conta do beneficiario não exista")
    @Transactional
    void testaLancarExcecaoContaPaganteNaoExiste() {
        TransferenciaDto transferencia =
                new TransferenciaDto()
                        .setContaBeneficiario(beneficiario.getId())
                        .setContaPagante(10)
                        .setValor(new BigDecimal("101.00"));

        assertThrows(
                IllegalArgumentException.class,
                () -> transferenciaService.criarTransferencia(transferencia)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção caso valor da transferencia seja inválido")
    @Transactional
    void testaLancarExcecaoValorTransferenciaInvalido () {
        TransferenciaDto transferencia =
                new TransferenciaDto()
                        .setContaBeneficiario(beneficiario.getId())
                        .setContaPagante(pagante.getId())
                        .setValor(new BigDecimal("0"));

        assertThrows(
                IllegalArgumentException.class,
                () -> transferenciaService.criarTransferencia(transferencia)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção caso conta beneficiária e pagante sejam iguais")
    @Transactional
    void testaLancarExcecaoContaBeneficiarioPaganteIguais() {
        TransferenciaDto transferencia =
                new TransferenciaDto()
                        .setContaBeneficiario(beneficiario.getId())
                        .setContaPagante(beneficiario.getId())
                        .setValor(new BigDecimal("101.00"));

        assertThrows(
                IllegalArgumentException.class,
                () -> transferenciaService.criarTransferencia(transferencia)
        );
    }
}