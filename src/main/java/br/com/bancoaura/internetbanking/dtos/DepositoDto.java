package br.com.bancoaura.internetbanking.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class DepositoDto {
    @NotNull
    private Integer contaBeneficiario;
    @NotNull
    private BigDecimal valor;

    public Integer getContaBeneficiario() {
        return contaBeneficiario;
    }

    public DepositoDto setContaBeneficiario(Integer contaBeneficiario) {
        this.contaBeneficiario = contaBeneficiario;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public DepositoDto setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }
}
