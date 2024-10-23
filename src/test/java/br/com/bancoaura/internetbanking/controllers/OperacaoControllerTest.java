package br.com.bancoaura.internetbanking.controllers;

import br.com.bancoaura.internetbanking.auxiliares.AuxiliarJwt;
import br.com.bancoaura.internetbanking.dtos.ClienteDto;
import br.com.bancoaura.internetbanking.dtos.ContaDto;
import br.com.bancoaura.internetbanking.dtos.DepositoDto;
import br.com.bancoaura.internetbanking.dtos.TransferenciaDto;
import br.com.bancoaura.internetbanking.entidades.Cliente;
import br.com.bancoaura.internetbanking.entidades.Conta;
import br.com.bancoaura.internetbanking.repositorios.ContaRepository;
import br.com.bancoaura.internetbanking.servicos.ContaClienteService;
import br.com.bancoaura.internetbanking.servicos.TransferenciaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(OperacaoController.class)
class OperacaoControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TransferenciaService transferenciaService;

    @MockBean
    ContaClienteService contaClienteService;

    @MockBean
    AuxiliarJwt auxiliarJwt;

    @InjectMocks
    OperacaoController operacaoController;

    TransferenciaDto mockTransferencia;
    DepositoDto mockDeposito, mockDepositoErro;
    ContaDto mockConta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(operacaoController).build();

        this.mockTransferencia =
                new TransferenciaDto()
                        .setContaPagante(1)
                        .setContaBeneficiario(2)
                        .setValor(new BigDecimal("1"));

        this.mockDeposito =
                new DepositoDto()
                    .setContaBeneficiario(1)
                    .setValor(new BigDecimal("2"));

        this.mockConta = new ContaDto().setId(1).setSaldo(new BigDecimal("2"));

        Mockito.when(transferenciaService.criarTransferencia(mockTransferencia)).thenReturn(mockTransferencia);
        Mockito.when(contaClienteService.depositarContaCliente(mockDeposito)).thenReturn(mockConta);
    }

    @Test
    @DisplayName("Testa criar uma transferência com dados válidos")
    void criarTransferencia() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(
            post("/api/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockTransferencia))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Testa depositar um valor em uma conta")
    void depositarValor() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(
                post("/api/depositar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockDeposito))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}