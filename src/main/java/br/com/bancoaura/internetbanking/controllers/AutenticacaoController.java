package br.com.bancoaura.internetbanking.controllers;

import br.com.bancoaura.internetbanking.dtos.ContaDto;
import br.com.bancoaura.internetbanking.servicos.AutenticacaoService;
import br.com.bancoaura.internetbanking.servicos.ContaClienteService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class AutenticacaoController {

    @Autowired
    AutenticacaoService autenticacaoService;

    @PostMapping("/login")
    public ResponseEntity<?> fazerLogin(@RequestBody ContaDto contaDto) {
        try {
            ContaDto conta = autenticacaoService.autenticar(contaDto.getId(), contaDto.getSenha());

            return ResponseEntity.ok().body(conta);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
