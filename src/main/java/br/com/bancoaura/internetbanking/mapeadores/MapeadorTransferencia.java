package br.com.bancoaura.internetbanking.mapeadores;

import br.com.bancoaura.internetbanking.dtos.TransferenciaDto;
import br.com.bancoaura.internetbanking.entidades.Transferencia;

public class MapeadorTransferencia {
    public static TransferenciaDto entidadeParaDto(Transferencia transferencia) {
        TransferenciaDto transferenciaDto = new TransferenciaDto();

        transferenciaDto.setId(transferencia.getId());
        transferenciaDto.setContaBeneficiario(transferencia.getContaBeneficiario());
        transferenciaDto.setContaPagante(transferencia.getContaPagante());
        transferenciaDto.setValor(transferencia.getValor());
        transferenciaDto.setData(transferencia.getData());
        transferenciaDto.setNomeBeneficiario(transferencia.getNomeBeneficiario());
        transferenciaDto.setNomePagante(transferencia.getNomePagante());

        return transferenciaDto;
    }
}
