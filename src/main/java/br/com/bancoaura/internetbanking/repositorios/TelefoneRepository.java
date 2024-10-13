package br.com.bancoaura.internetbanking.repositories;

import br.com.bancoaura.internetbanking.entidades.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
