package br.com.bancoaura.internetbanking.entidades;

import jakarta.persistence.*;

@Entity
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private String numero;

    public Cliente getCliente() {
        return cliente;
    }

    public String getNumero() {
        return numero;
    }

    public Telefone setCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public Telefone setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public Long getId() {
        return id;
    }
}
