package br.com.bancoaura.internetbanking.repositorios;

import br.com.bancoaura.internetbanking.entidades.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest  // Carrega apenas os componentes JPA, sem carregar o contexto da aplicação inteiro
@ActiveProfiles("test")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Deve salvar um cliente no banco de dados")
    void testaSalvarCliente() {
        // Dado um novo cliente
        Cliente cliente = new Cliente().setCpf("23194509047").setNome("João Silva").setEmail("joaosilva@gmail.com");

        // Quando o cliente for salvo
        Cliente savedCliente = clienteRepository.save(cliente);

        // Então o cliente deve ser salvo com sucesso e deve ter um ID gerado
        assertNotNull(savedCliente.getId());
        assertEquals("João Silva", savedCliente.getNome());
        assertEquals("23194509047", savedCliente.getCpf());
    }

    @Test
    @DisplayName("Deve encontrar um cliente pelo ID")
    void testaEncontrarClientePeloId() {
        Cliente cliente = new Cliente().setNome("João Silva").setCpf("23194509047").setEmail("joaosilva@gmail.com");
        Cliente clienteSalvo = clienteRepository.save(cliente);

        Optional<Cliente> clienteEncontrado = clienteRepository.findById(clienteSalvo.getId());

        assertTrue(clienteEncontrado.isPresent());
        assertEquals("João Silva", clienteEncontrado.get().getNome());
    }

    @Test
    @DisplayName("Deve deletar um cliente")
    void testaDeletarCliente() {
        Cliente cliente = new Cliente().setNome("João Silva").setCpf("23194509047").setEmail("joaosilva@gmail.com");
        Cliente clienteSalvo = clienteRepository.save(cliente);

        clienteRepository.delete(clienteSalvo);

        Optional<Cliente> clienteEncontrado = clienteRepository.findById(clienteSalvo.getId());
        assertFalse(clienteEncontrado.isPresent());
    }

    @Test
    @DisplayName("Deve alterar dados do cliente")
    void testaAlterarDadosCliente() {
        Cliente cliente = new Cliente().setNome("João Silva").setCpf("23194509047").setEmail("joaosilva@gmail.com");
        Cliente clienteSalvo = clienteRepository.save(cliente);

        clienteSalvo.setNome("José das Couves");
        clienteSalvo.setCpf("66061408030");
        Cliente clienteAtualizado = clienteRepository.save(clienteSalvo);

        assertNotEquals("João Silva", clienteAtualizado.getNome());
        assertNotEquals("23194509047", clienteAtualizado.getCpf());
    }

    @Test
    @DisplayName("Deve lançar exceção ao inserir um cliente com CPF já cadastrado")
    void testaLancarExcecaoCpfJaCadastrado() {
        Cliente c1 = new Cliente().setCpf("23194509047").setNome("João Silva").setEmail("joaosilva@gmail.com");
        Cliente c2 = new Cliente().setCpf("23194509047").setNome("José das Couves").setEmail("josedascouves@gmail.com");

        Cliente c1Salvo = clienteRepository.save(c1);

        assertNotNull(c1Salvo.getId());

        assertThrows(
            DataIntegrityViolationException.class,
            () -> clienteRepository.save(c2)
        );
    }

    @Test
    @DisplayName("Lança exceção ao inserir cliente com nome com mais de 80 caracteres")
    void testaLancarExcecaoNomeComMaisDe80Caracteres() {
        Cliente cliente = new Cliente()
                                .setCpf("23194509047")
                                .setEmail("pedroalcantara@gmail.com")
                                .setNome("Pedro de Alcântara Francisco António João Carlos Xavier de Paula Miguel Rafael Joaquim José Gonzaga Pascoal Cipriano Serafim");

        assertThrows(DataIntegrityViolationException.class, () -> clienteRepository.save(cliente));
    }
}
