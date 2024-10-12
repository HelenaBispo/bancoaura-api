package br.com.bancoaura.internetbanking.repositories;

import br.com.bancoaura.internetbanking.entidades.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenciaRepository extends JpaRepository<Transferencia, String> {
}
