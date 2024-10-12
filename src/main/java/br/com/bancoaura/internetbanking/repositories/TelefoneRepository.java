package br.com.bancoaura.internetbanking.repositories;

import br.com.bancoaura.internetbanking.entidades.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
