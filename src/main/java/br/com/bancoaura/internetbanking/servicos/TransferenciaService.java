package br.com.bancoaura.internetbanking.servicos;

import br.com.bancoaura.internetbanking.dtos.TransferenciaDto;

public interface TransferenciaService {
    TransferenciaDto criarTransferencia(TransferenciaDto transferenciaDto);
}
