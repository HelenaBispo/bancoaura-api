package br.com.bancoaura.internetbanking.repositorios;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"br.com.bancoaura.internetbanking.entidades"})
@EnableJpaRepositories(basePackages = {"br.com.bancoaura.internetbanking.repositorios"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
