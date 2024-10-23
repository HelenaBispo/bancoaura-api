package br.com.bancoaura.internetbanking.servicos;

import br.com.bancoaura.internetbanking.dtos.ClienteDto;
import br.com.bancoaura.internetbanking.dtos.ContaDto;
import br.com.bancoaura.internetbanking.dtos.DepositoDto;

public interface ContaClienteService {
    ContaDto criarContaCliente(ClienteDto clienteDto);
    ContaDto obterContaCliente(Integer id);
    void deletarContaCliente(Integer id);
    ContaDto depositarContaCliente(DepositoDto depositoDto);
}
