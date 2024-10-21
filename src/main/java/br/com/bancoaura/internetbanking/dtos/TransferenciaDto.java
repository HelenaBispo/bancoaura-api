package br.com.bancoaura.internetbanking.dtos;

import org.springframework.format.datetime.DateFormatter;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransferenciaDto {
    private String id;
    private Integer contaPagante;
    private Integer contaBeneficiario;
    private String nomePagante;
    private String nomeBeneficiario;
    private Timestamp data;
    private BigDecimal valor;

    public TransferenciaDto(String id, Integer contaPagante, Integer contaBeneficiario, String nomePagante, String nomeBeneficiario, Timestamp data, BigDecimal valor) {
        DateFormatter formatter = new DateFormatter();
        this.id = id;
        this.contaPagante = contaPagante;
        this.contaBeneficiario = contaBeneficiario;
        this.nomePagante = nomePagante;
        this.nomeBeneficiario = nomeBeneficiario;
        this.data = data;
        this.valor = valor;
    }

    public TransferenciaDto(BigDecimal valor, String nomeBeneficiario, String nomePagante, Integer contaBeneficiario, Integer contaPagante) {
        this.valor = valor;
        this.nomeBeneficiario = nomeBeneficiario;
        this.nomePagante = nomePagante;
        this.contaBeneficiario = contaBeneficiario;
        this.contaPagante = contaPagante;
    }

    public TransferenciaDto() {
    }

    public String getId() {
        return id;
    }

    public TransferenciaDto setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getContaPagante() {
        return contaPagante;
    }

    public TransferenciaDto setContaPagante(Integer contaPagante) {
        this.contaPagante = contaPagante;
        return this;
    }

    public Integer getContaBeneficiario() {
        return contaBeneficiario;
    }

    public TransferenciaDto setContaBeneficiario(Integer contaBeneficiario) {
        this.contaBeneficiario = contaBeneficiario;
        return this;
    }

    public String getNomePagante() {
        return nomePagante;
    }

    public TransferenciaDto setNomePagante(String nomePagante) {
        this.nomePagante = nomePagante;
        return this;
    }

    public String getNomeBeneficiario() {
        return nomeBeneficiario;
    }

    public TransferenciaDto setNomeBeneficiario(String nomeBeneficiario) {
        this.nomeBeneficiario = nomeBeneficiario;
        return this;
    }

    public Timestamp getData() {
        return data;
    }

    public TransferenciaDto setData(Timestamp data) {
        this.data = data;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TransferenciaDto setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }
}
