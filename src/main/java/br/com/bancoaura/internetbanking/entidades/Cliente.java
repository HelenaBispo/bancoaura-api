package br.com.bancoaura.internetbanking.entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefone> telefones;

    @OneToOne(mappedBy = "titular")
    private Conta conta;

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    public Cliente setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Cliente setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Cliente setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public Cliente setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
        return this;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }
}
