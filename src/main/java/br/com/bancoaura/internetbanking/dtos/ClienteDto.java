package br.com.bancoaura.internetbanking.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class ClienteDto {
    private Integer id;

    private Integer numeroConta;

    @NotBlank(message = "É obrigatório informar o nome do cliente")
    @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
    private String nome;

    @NotBlank(message = "É obrigatório informar o CPF do cliente")
    @Size(min = 11, max = 11, message = "O CPF deve possuir 11 dígitos")
    private String cpf;

    @NotEmpty
    private List<String> telefones;

    @NotBlank(message = "É obrigatório informar um e-mail")
    @Email(message = "Informe um e-mail válido")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String senha;

    public ClienteDto(Integer id, Integer numeroConta, String nome, String cpf, List<String> telefones, String email, String senha) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.nome = nome;
        this.cpf = cpf;
        this.telefones = telefones;
        this.email = email;
        this.senha = senha;
    }

    public ClienteDto() {
        super();
        this.telefones = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public ClienteDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getNumeroConta() {
        return numeroConta;
    }

    public ClienteDto setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ClienteDto setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getCpf() {
        return cpf;
    }

    public ClienteDto setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public ClienteDto setTelefones(List<String> telefones) {
        this.telefones = telefones;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ClienteDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public ClienteDto setSenha(String senha) {
        this.senha = senha;
        return this;
    }
}
