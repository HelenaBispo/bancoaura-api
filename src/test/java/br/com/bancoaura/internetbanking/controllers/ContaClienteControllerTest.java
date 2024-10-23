package br.com.bancoaura.internetbanking.controllers;

import br.com.bancoaura.internetbanking.auxiliares.AuxiliarJwt;
import br.com.bancoaura.internetbanking.dtos.ClienteDto;
import br.com.bancoaura.internetbanking.mapeadores.MapeadorContaCliente;

import br.com.bancoaura.internetbanking.servicos.ContaClienteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ContaClienteController.class)
class ContaClienteControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    ContaClienteService contaClienteService;

    @MockBean
    AuxiliarJwt auxiliarJwt;

    @InjectMocks
    ContaClienteController contaClienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.mvc = MockMvcBuilders.standaloneSetup(contaClienteController).build();
    }

    @Test
    @DisplayName("Deve retornar o status 201 - CREATED ao informar dados do cliente")
    void testaCriarContaCliente() throws Exception {
        List<String> telefones = Arrays.asList("83988887777");
        ClienteDto clienteDto = new ClienteDto()
                                    .setCpf("23194509047")
                                    .setNome("João Silva")
                                    .setTelefones(telefones)
                                    .setEmail("joaosilva@gmail.com")
                                    .setSenha("12341234");

        mvc.perform(
                post("/api/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MapeadorContaCliente.dtoParaString(clienteDto))
            )
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @AfterEach
    void tearDown() {
        this.mvc = null;
    }
}