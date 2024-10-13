package br.com.bancoaura.internetbanking.repositorios;

import br.com.bancoaura.internetbanking.entidades.Cliente;
import br.com.bancoaura.internetbanking.entidades.Telefone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TelefoneRepositoryTest {

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        Cliente c = new Cliente().setCpf("23194509047").setNome("João Silva").setId(1);
        cliente = clienteRepository.save(c);
    }

    @Test
    @DisplayName("Deve inserir um telefone relacionado a um cliente")
    void testaSalvarTelefone() {
        Telefone t = new Telefone().setCliente(cliente).setNumero("11988887777");

        Telefone telefone = telefoneRepository.save(t);

        assertNotNull(telefone.getId());
        assertEquals(telefone.getCliente(), t.getCliente());
        assertEquals(telefone.getNumero(), t.getNumero());
    }

    @Test
    @DisplayName("Deve remover um telefone existente")
    void testaRemoverTelefone() {
        Telefone t = new Telefone().setCliente(cliente).setNumero("11988887777");

        Telefone telefone = telefoneRepository.save(t);

        assertNotNull(telefone.getId());

        telefoneRepository.delete(telefone);

        Optional<Telefone> telefoneOptional = telefoneRepository.findById(telefone.getId());
        assertFalse(telefoneOptional.isPresent());
    }

    @AfterEach
    void tearDown() {
        cliente = null;
        clienteRepository.deleteAll();
    }
}