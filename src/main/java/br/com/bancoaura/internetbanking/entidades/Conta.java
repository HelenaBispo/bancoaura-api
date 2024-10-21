package br.com.bancoaura.internetbanking.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente titular;

    @Column(nullable = false)
    private String hashSenha;

    @OneToMany(mappedBy = "pagante", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transferencia> transferenciasPagas;

    @OneToMany(mappedBy = "beneficiario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transferencia> transferenciasRecebidas;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal saldo;

    public Conta() {
        this.saldo = new BigDecimal(0);
        this.transferenciasRecebidas = new ArrayList<Transferencia>();
        this.transferenciasPagas = new ArrayList<>();
    }

    public Cliente getTitular() {
        return titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Conta setCliente(Cliente titular) {
        this.titular = titular;
        return this;
    }

    public Conta setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Conta setId(Integer id) {
        this.id = id;
        return this;
    }

    public List<Transferencia> getTransferenciasPagas() {
        return transferenciasPagas;
    }

    public List<Transferencia> getTransferenciasRecebidas() {
        return transferenciasRecebidas;
    }

    public String getHashSenha() {
        return hashSenha;
    }

    public Conta setHashSenha(String hashSenha) {
        this.hashSenha = hashSenha;
        return this;
    }
}
