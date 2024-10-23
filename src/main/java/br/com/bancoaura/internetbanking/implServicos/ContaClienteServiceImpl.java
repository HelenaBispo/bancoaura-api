package br.com.bancoaura.internetbanking.implServicos;

import br.com.bancoaura.internetbanking.dtos.ClienteDto;
import br.com.bancoaura.internetbanking.dtos.ContaDto;
import br.com.bancoaura.internetbanking.dtos.DepositoDto;
import br.com.bancoaura.internetbanking.entidades.Cliente;
import br.com.bancoaura.internetbanking.entidades.Conta;
import br.com.bancoaura.internetbanking.entidades.Telefone;
import br.com.bancoaura.internetbanking.mapeadores.MapeadorContaCliente;
import br.com.bancoaura.internetbanking.repositorios.ClienteRepository;
import br.com.bancoaura.internetbanking.repositorios.ContaRepository;
import br.com.bancoaura.internetbanking.repositorios.TelefoneRepository;
import br.com.bancoaura.internetbanking.servicos.ContaClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static br.com.bancoaura.internetbanking.auxiliares.AuxiliarValidacao.validarCpf;

@Service
public class ContaClienteServiceImpl implements ContaClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    TelefoneRepository telefoneRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    @Transactional
    public ContaDto criarContaCliente(ClienteDto clienteDto) {

        if ( !validarCpf(clienteDto.getCpf()) ) {
            throw new IllegalArgumentException("CPF inválido");
        }

        if ( clienteDto.getTelefones().isEmpty() ) {
            throw new IllegalArgumentException("Nenhum telefone foi informado");
        }

        Cliente c = new Cliente()
                        .setCpf(clienteDto.getCpf())
                        .setNome(clienteDto.getNome())
                        .setEmail(clienteDto.getEmail());

        Cliente clienteSalvo;

        clienteSalvo = clienteRepository.save(c);

        List<Telefone> telefones = clienteDto
                .getTelefones()
                .stream()
                .map(
                        numeroTelefone -> new Telefone().setNumero(numeroTelefone).setCliente(clienteSalvo)
                ).toList();

        try {
            telefoneRepository.saveAll(telefones);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("O cliente informou dois ou mais números repetidos");
        }

        BCryptPasswordEncoder hasher = new BCryptPasswordEncoder();

        Conta conta = new Conta()
                            .setCliente(clienteSalvo)
                            .setHashSenha(hasher.encode(clienteDto.getSenha()));

        var contaSalva = contaRepository.save(conta);

        return MapeadorContaCliente.entidadeParaDto(contaSalva);
    }

    @Override
    public ContaDto obterContaCliente(Integer id) {

        Optional<Conta> resultadoConta = contaRepository.findById(id);

        if (resultadoConta.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma conta encontrada");
        }

        return MapeadorContaCliente.entidadeParaDto(resultadoConta.get());
    }

    public ContaDto depositarContaCliente(DepositoDto depositoDto) {

        if (depositoDto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito não pode ser menor ou igual a zero");
        }

        Conta conta = contaRepository.findById(depositoDto.getContaBeneficiario())
                        .orElseThrow(() -> new EntityNotFoundException("Nenhuma conta encontrada"));

        conta.setSaldo(conta.getSaldo().add(depositoDto.getValor()));

        contaRepository.save(conta);

        return MapeadorContaCliente.entidadeParaDto(conta);
    }

    @Override
    public void deletarContaCliente(Integer id) {
        Conta conta = contaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nenhuma conta encontrada"));
        contaRepository.deleteById(id);
        clienteRepository.deleteById(conta.getTitular().getId());
    }
}
