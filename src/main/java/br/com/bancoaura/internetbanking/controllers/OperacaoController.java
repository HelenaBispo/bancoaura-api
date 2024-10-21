package br.com.bancoaura.internetbanking.controllers;

import br.com.bancoaura.internetbanking.dtos.ContaDto;
import br.com.bancoaura.internetbanking.dtos.DepositoDto;
import br.com.bancoaura.internetbanking.dtos.TransferenciaDto;
import br.com.bancoaura.internetbanking.servicos.ContaClienteService;
import br.com.bancoaura.internetbanking.servicos.TransferenciaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OperacaoController {

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    private ContaClienteService contaClienteService;

    @PostMapping("/transferir")
    public ResponseEntity<?> criarTransferencia(@RequestBody TransferenciaDto transferenciaDto) {
        try {

            TransferenciaDto transferencia = transferenciaService.criarTransferencia(transferenciaDto);

            return ResponseEntity.ok().body(transferencia);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/depositar")
    public ResponseEntity<?> depositar(@Valid @RequestBody DepositoDto depositoDto) {
        try {

            ContaDto contaDto = contaClienteService.depositarContaCliente(depositoDto);
            return ResponseEntity.ok().body(contaDto);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (EntityNotFoundException e) {

            return ResponseEntity.notFound().build();

        }
    }
}
