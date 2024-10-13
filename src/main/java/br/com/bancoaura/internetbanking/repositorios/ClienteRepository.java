package br.com.bancoaura.internetbanking.repositorios;

import br.com.bancoaura.internetbanking.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
