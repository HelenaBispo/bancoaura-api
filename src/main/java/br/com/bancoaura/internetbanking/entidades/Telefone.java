package br.com.bancoaura.internetbanking.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"cliente_id", "numero"})})
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Cliente cliente;

    @Column(nullable = false, length = 11)
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

    @Override
    public String toString() {
        return String.format("Telefone [numero=%s]", numero);
    }
}
