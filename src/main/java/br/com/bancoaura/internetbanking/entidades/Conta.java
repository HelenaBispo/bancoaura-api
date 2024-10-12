package br.com.bancoaura.internetbanking.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente titular;

    @OneToMany(mappedBy = "pagante")
    @JsonManagedReference
    private List<Transferencia> transferenciasPagas;

    @OneToMany(mappedBy = "beneficiario")
    @JsonManagedReference
    private List<Transferencia> transferenciasRecebidas;

    @Column(nullable = false, precision = 14, scale = 2)
    private Double saldo;

    public Conta() {
        this.saldo = 0.0;
    }

    public Cliente getTitular() {
        return titular;
    }

    public Conta setCliente(Cliente titular) {
        this.titular = titular;
        return this;
    }

    public Conta setSaldo(Double saldo) {
        this.saldo = saldo;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public List<Transferencia> getTransferenciasPagas() {
        return transferenciasPagas;
    }

    public List<Transferencia> getTransferenciasRecebidas() {
        return transferenciasRecebidas;
    }
}
