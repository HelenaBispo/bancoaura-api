package br.com.bancoaura.internetbanking.dtos;

import java.math.BigDecimal;
import java.util.List;

public class ContaDto {
    private Integer id;
    private String cpfTitular;
    private String nomeTitular;
    private BigDecimal saldo;
    private List<TransferenciaDto> transferencias;
    private String senha;
    private String token;

    public ContaDto(Integer id, String nomeTitular, BigDecimal saldo) {
        this.id = id;
        this.nomeTitular = nomeTitular;
        this.saldo = saldo;
    }

    public ContaDto(Integer id, String cpf, String nomeTitular, BigDecimal saldo) {
        this.id = id;
        this.nomeTitular = nomeTitular;
        this.saldo = saldo;
        this.cpfTitular = cpf;
    }

    public ContaDto(Integer id, String cpfTitular, String nomeTitular, BigDecimal saldo, List<TransferenciaDto> transferencias, String senha) {
        this.id = id;
        this.nomeTitular = nomeTitular;
        this.saldo = saldo;
        this.transferencias = transferencias;
        this.cpfTitular = cpfTitular;
        this.senha = senha;
    }

    public ContaDto(Integer id, String nomeTitular) {
        this.id = id;
        this.nomeTitular = nomeTitular;
        this.saldo = BigDecimal.ZERO;
    }

    public ContaDto() {
    }

    public Integer getId() {
        return id;
    }

    public ContaDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public ContaDto setToken(String token) {
        this.token = token;
        return this;
    }

    public String getToken() {
        return token;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public ContaDto setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
        return this;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public ContaDto setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public List<TransferenciaDto> getTransferencias() {
        return transferencias;
    }

    public ContaDto setTransferencias(List<TransferenciaDto> transferencias) {
        this.transferencias = transferencias;
        return this;
    }

    public String getCpfTitular() {
        return cpfTitular;
    }

    public ContaDto setCpfTitular(String cpfTitular) {
        this.cpfTitular = cpfTitular;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public ContaDto setSenha(String senha) {
        this.senha = senha;
        return this;
    }
}
