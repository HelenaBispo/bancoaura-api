package br.com.bancoaura.internetbanking.controllers;

import br.com.bancoaura.internetbanking.dtos.ClienteDto;
import br.com.bancoaura.internetbanking.dtos.ContaDto;
import br.com.bancoaura.internetbanking.servicos.ContaClienteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contas")
public class ContaClienteController {

    @Autowired
    private ContaClienteService contaClienteService;

    @PostMapping
    public ResponseEntity<?> criarContaCliente(@Valid @RequestBody ClienteDto clienteDto) {
        try {

            contaClienteService.criarContaCliente(clienteDto);
            return ResponseEntity.status(201).build();

        } catch (IllegalArgumentException | DataIntegrityViolationException ex) {

            return ResponseEntity.badRequest().body(ex.getMessage());

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalharContaCliente(@PathVariable Integer id) {
        try {

            ContaDto contaDto = contaClienteService.obterContaCliente(id);

            return ResponseEntity.status(200).body(contaDto);

        } catch (IllegalArgumentException ex) {

            return ResponseEntity.status(404).body(ex.getMessage());

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarContaCliente(@PathVariable Integer id) {
        try {

            contaClienteService.deletarContaCliente(id);

            return ResponseEntity.status(204).build();

        } catch (EntityNotFoundException ex) {

            return ResponseEntity.status(404).body(ex.getMessage());

        }
    }
}
