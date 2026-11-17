package br.com.eseg.legaltech.controller;

import br.com.eseg.legaltech.dto.VinculoDTO;
import br.com.eseg.legaltech.model.Vinculo;
import br.com.eseg.legaltech.service.VinculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vinculos")
public class VinculoController {

    private final VinculoService vinculoService;

    public VinculoController(VinculoService vinculoService) {
        this.vinculoService = vinculoService;
    }

    @PostMapping
    public ResponseEntity<Vinculo> criar(@RequestBody @Valid VinculoDTO dto) {
        Vinculo novoVinculo = vinculoService.criarVinculo(dto.clienteId(), dto.legaltechId(), dto.dataInicio());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoVinculo);
    }

    @GetMapping
    public ResponseEntity<List<Vinculo>> listarTodos() {
        return ResponseEntity.ok(vinculoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vinculo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vinculoService.buscarPorId(id));
    }
}