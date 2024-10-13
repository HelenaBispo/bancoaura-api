package br.com.bancoaura.internetbanking.repositorios;

import br.com.bancoaura.internetbanking.entidades.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, String> {
}
