package br.com.bancoaura.internetbanking.implServicos;

import br.com.bancoaura.internetbanking.dtos.TransferenciaDto;
import br.com.bancoaura.internetbanking.entidades.Conta;
import br.com.bancoaura.internetbanking.entidades.Transferencia;
import br.com.bancoaura.internetbanking.mapeadores.MapeadorTransferencia;
import br.com.bancoaura.internetbanking.repositorios.ContaRepository;
import br.com.bancoaura.internetbanking.repositorios.TransferenciaRepository;
import br.com.bancoaura.internetbanking.servicos.TransferenciaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {
    @Autowired
    TransferenciaRepository transferenciaRepository;

    @Autowired
    ContaRepository contaRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransferenciaDto criarTransferencia(TransferenciaDto transferenciaDto) {
        if (transferenciaDto.getContaPagante() == null) {
            throw new IllegalArgumentException("A conta do pagante não foi informada");
        }

        if (transferenciaDto.getContaBeneficiario() == null) {
            throw new IllegalArgumentException("A conta do beneficiário não foi informada");
        }

        if (transferenciaDto.getValor() == null) {
            throw new IllegalArgumentException("O valor da transferência não foi informada");
        }

        if (transferenciaDto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência não pode ser menor ou igual a zero");
        }

        if (transferenciaDto.getContaPagante().equals(transferenciaDto.getContaBeneficiario())) {
            throw new IllegalArgumentException("A conta do pagante e do beneficiário não podem ser iguais");
        }

        Conta pagante =
                contaRepository.findById(transferenciaDto.getContaPagante())
                        .orElseThrow(() -> new IllegalArgumentException("Nenhum pagante encontrado com o número informado"));

        Conta beneficiario =
                contaRepository.findById(transferenciaDto.getContaBeneficiario())
                        .orElseThrow(() -> new IllegalArgumentException("Nenhum beneficiário encontrado com o número informado"));

        if (pagante.getSaldo().compareTo(transferenciaDto.getValor()) < 0) {
            throw new IllegalArgumentException("O saldo em conta é menor que o valor da transferência");
        }

        Transferencia novaTransferencia = new Transferencia()
                .setBeneficiario(beneficiario)
                .setPagante(pagante)
                .setValor(transferenciaDto.getValor())
                .setContaPagante(pagante.getId())
                .setContaBeneficiario(beneficiario.getId())
                .setNomeBeneficiario(beneficiario.getTitular().getNome())
                .setNomePagante(pagante.getTitular().getNome());

        pagante.setSaldo(pagante.getSaldo().subtract(transferenciaDto.getValor()));
        beneficiario.setSaldo(beneficiario.getSaldo().add(transferenciaDto.getValor()));

        contaRepository.save(pagante);
        contaRepository.save(beneficiario);

        Transferencia transferenciaSalva = transferenciaRepository.save(novaTransferencia);

        return MapeadorTransferencia.entidadeParaDto(transferenciaSalva);
    }
}
