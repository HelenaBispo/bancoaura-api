package br.com.bancoaura.internetbanking.repositories;

import br.com.bancoaura.internetbanking.entidades.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
}
