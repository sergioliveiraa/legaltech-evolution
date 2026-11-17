package br.com.eseg.legaltech.service;

import br.com.eseg.legaltech.exception.BusinessRuleException;
import br.com.eseg.legaltech.exception.ResourceNotFoundException;
import br.com.eseg.legaltech.model.Documento;
import br.com.eseg.legaltech.model.EventoDocumento;
import br.com.eseg.legaltech.repository.DocumentoRepository;
import br.com.eseg.legaltech.repository.EventoDocumentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final EventoDocumentoRepository eventoDocumentoRepository;
    private final VinculoService vinculoService;

    public DocumentoService(DocumentoRepository documentoRepository,
                            EventoDocumentoRepository eventoDocumentoRepository,
                            VinculoService vinculoService) {
        this.documentoRepository = documentoRepository;
        this.eventoDocumentoRepository = eventoDocumentoRepository;
        this.vinculoService = vinculoService;
    }

    @Transactional
    public EventoDocumento registrarEvento(Long documentoId, String status, boolean revisaoHumana) {
        Documento documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado ID: " + documentoId));

        vinculoService.validarDataFato(documento.getVinculo().getId(), LocalDate.now());

        if (("APROVADO".equalsIgnoreCase(status) || "ASSINADO".equalsIgnoreCase(status)) && !revisaoHumana) {
            throw new BusinessRuleException(
                    "Violação da RN-04: Documentos com status 'APROVADO' ou 'ASSINADO' exigem obrigatoriamente confirmação de revisão humana."
            );
        }

        EventoDocumento evento = EventoDocumento.builder()
                .documento(documento)
                .statusDocumento(status.toUpperCase())
                .revisaoHumana(revisaoHumana)
                .dataEvento(LocalDateTime.now())
                .build();

        return eventoDocumentoRepository.save(evento);
    }
}