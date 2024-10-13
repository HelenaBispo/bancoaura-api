package br.com.bancoaura.internetbanking.repositorios;

import br.com.bancoaura.internetbanking.entidades.Cliente;
import br.com.bancoaura.internetbanking.entidades.Conta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest  // Carrega apenas os componentes JPA, sem carregar o contexto da aplicação inteiro
@ActiveProfiles("test")
class ContaRepositoryTest {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        Cliente c = new Cliente().setCpf("23194509047").setNome("João Silva").setId(1);
        cliente = clienteRepository.save(c);
    }

    @Test
    @DisplayName("Deve inserir uma conta relacionada ao cliente")
    void testaCriarConta() {
        Conta conta = new Conta().setCliente(cliente);
        Conta contaSalva = contaRepository.save(conta);

        assertNotNull(contaSalva.getId());
        assertEquals(contaSalva.getTitular(), conta.getTitular());
        assertEquals(contaSalva.getSaldo(), conta.getSaldo());
        assertTrue(contaSalva.getTransferenciasPagas().isEmpty());
        assertTrue(contaSalva.getTransferenciasRecebidas().isEmpty());
    }

    @AfterEach
    void tearDown() {
        cliente = null;
        clienteRepository.deleteAll();
    }
}