package br.com.bancoaura.internetbanking.repositories;

import br.com.bancoaura.internetbanking.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
