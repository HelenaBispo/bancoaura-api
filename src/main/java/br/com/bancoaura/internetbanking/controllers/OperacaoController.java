package br.com.bancoaura.internetbanking.controllers;

import br.com.bancoaura.internetbanking.dtos.TransferenciaDto;
import br.com.bancoaura.internetbanking.servicos.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @PostMapping
    public ResponseEntity<?> criarTransferencia(@RequestBody TransferenciaDto transferenciaDto) {
        try {

            TransferenciaDto transferencia = transferenciaService.criarTransferencia(transferenciaDto);

            return ResponseEntity.ok().body(transferencia);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
}
