package br.com.bancoaura.internetbanking.entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
public class Transferencia {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "pagante_id", nullable = false)
    private Conta pagante;

    @ManyToOne
    @JoinColumn(name = "beneficiario_id", nullable = false)
    private Conta beneficiario;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false, precision = 14, scale = 2)
    private Double valor;

    public Transferencia() {
        this.valor = 0.0;
        this.data = LocalDateTime.now();
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

    public LocalDateTime getData() {
        return data;
    }

    public Double getValor() {
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

    public Transferencia setValor(Double valor) {
        this.valor = valor;
        return this;
    }
}
