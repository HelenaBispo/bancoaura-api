package br.com.bancoaura.internetbanking.mapeadores;

import br.com.bancoaura.internetbanking.dtos.ClienteDto;
import br.com.bancoaura.internetbanking.dtos.ContaDto;
import br.com.bancoaura.internetbanking.dtos.TransferenciaDto;
import br.com.bancoaura.internetbanking.entidades.Conta;
import br.com.bancoaura.internetbanking.entidades.Transferencia;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MapeadorContaCliente {
    public static ContaDto entidadeParaDto(Conta conta) {
        ObjectMapper mapper = new ObjectMapper();
        ContaDto contaDto = new ContaDto();

        contaDto.setId(conta.getId());
        contaDto.setSaldo(conta.getSaldo());
        contaDto.setNomeTitular(conta.getTitular().getNome());
        contaDto.setCpfTitular(conta.getTitular().getCpf());

        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.addAll(conta.getTransferenciasPagas());
        transferencias.addAll(conta.getTransferenciasRecebidas());
        transferencias.sort(Comparator.comparing(Transferencia::getData));

        List<TransferenciaDto> transferenciaDtos =
                transferencias.stream()
                                .map(t -> mapper.convertValue(t, TransferenciaDto.class))
                                .toList();

        contaDto.setTransferencias(transferenciaDtos);

        return contaDto;
    }

    public static String dtoParaString(ClienteDto clienteDto) {
        String id, nome, cpf, email, senha, telefones;

        id = clienteDto.getId() == null ? "null" : clienteDto.getId().toString();
        nome = clienteDto.getNome();
        cpf = clienteDto.getCpf();
        email = clienteDto.getEmail();
        senha = clienteDto.getSenha();

        telefones = clienteDto.getTelefones().stream().map(t -> "\"" + t + "\"").collect(Collectors.joining(","));

        return String.format(
            "{\"nome\":\"%s\", \"cpf\":\"%s\", \"id\":%s, \"senha\":\"%s\", \"email\":\"%s\", \"telefones\":[%s]}",
            nome, cpf, id, senha, email, telefones
        );
    }
}
