package br.com.bancoaura.internetbanking.repositorios;

import br.com.bancoaura.internetbanking.entidades.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {
}
