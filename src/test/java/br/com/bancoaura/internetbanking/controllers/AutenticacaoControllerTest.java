package br.com.bancoaura.internetbanking.controllers;

import br.com.bancoaura.internetbanking.auxiliares.AuxiliarJwt;
import br.com.bancoaura.internetbanking.dtos.ContaDto;
import br.com.bancoaura.internetbanking.servicos.AutenticacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(AutenticacaoController.class)
class AutenticacaoControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    AuxiliarJwt auxiliarJwt;

    @MockBean
    AutenticacaoService autenticacaoService;

    @InjectMocks
    AutenticacaoController autenticacaoController;

    ContaDto mockConta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(autenticacaoController).build();

        mockConta = new ContaDto().setId(1).setSenha("12341234");

        ContaDto mockRetornoConta = mock(ContaDto.class);

        Mockito.when(autenticacaoService.autenticar(mockConta.getId(), mockConta.getSenha())).thenReturn(mockRetornoConta);
    }

    @Test
    void fazerLogin() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(
            post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockConta))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}