package br.com.bancoaura.internetbanking.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
public class Transferencia {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Conta pagante;

    @Column(nullable = false)
    private Integer contaPagante;

    @Column(nullable = false)
    private String nomePagante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Conta beneficiario;

    @Column(nullable = false)
    private Integer contaBeneficiario;

    @Column(nullable = false)
    private String nomeBeneficiario;

    @Column(nullable = false)
    private Timestamp data;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;

    public Transferencia() {
        this.valor = new BigDecimal(0);
        this.data = Timestamp.from(Instant.now());
    }

    public String getId() {
        return id;
    }

    public Conta getPagante() {
        return pagante;
    }

    public Conta getBeneficiario() {
        return beneficiario;
    }

    public Timestamp getData() {
        return data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Transferencia setPagante(Conta pagante) {
        this.pagante = pagante;
        return this;
    }

    public Transferencia setBeneficiario(Conta beneficiario) {
        this.beneficiario = beneficiario;
        return this;
    }

    public Transferencia setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public Integer getContaPagante() {
        return contaPagante;
    }

    public Transferencia setContaPagante(Integer contaPagante) {
        this.contaPagante = contaPagante;
        return this;
    }

    public String getNomePagante() {
        return nomePagante;
    }

    public Transferencia setNomePagante(String nomePagante) {
        this.nomePagante = nomePagante;
        return this;
    }

    public Integer getContaBeneficiario() {
        return contaBeneficiario;
    }

    public Transferencia setContaBeneficiario(Integer contaBeneficiario) {
        this.contaBeneficiario = contaBeneficiario;
        return this;
    }

    public String getNomeBeneficiario() {
        return nomeBeneficiario;
    }

    public Transferencia setNomeBeneficiario(String nomeBeneficiario) {
        this.nomeBeneficiario = nomeBeneficiario;
        return this;
    }
}
