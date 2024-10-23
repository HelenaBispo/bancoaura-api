package br.com.bancoaura.internetbanking.servicos;

import br.com.bancoaura.internetbanking.dtos.ContaDto;

public interface AutenticacaoService {
    ContaDto autenticar(Integer idConta, String senha);
}
