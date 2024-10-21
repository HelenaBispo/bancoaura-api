package br.com.bancoaura.internetbanking.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Telefone> telefones;

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String email;

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

    public String getEmail() {
        return email;
    }

    public Cliente setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Cliente [id=%s, nome=%s, cpf=%s]", id, nome, cpf);
    }
}
