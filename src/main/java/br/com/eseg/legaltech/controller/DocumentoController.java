package br.com.eseg.legaltech.controller;

import br.com.eseg.legaltech.dto.EventoDocumentoDTO;
import br.com.eseg.legaltech.model.EventoDocumento;
import br.com.eseg.legaltech.service.DocumentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/documentos")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping("/{id}/eventos")
    public ResponseEntity<EventoDocumento> registrarEvento(
            @PathVariable Long id,
            @RequestBody @Valid EventoDocumentoDTO dto) {

        EventoDocumento evento = documentoService.registrarEvento(id, dto.statusDocumento(), dto.revisaoHumana());
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }
}