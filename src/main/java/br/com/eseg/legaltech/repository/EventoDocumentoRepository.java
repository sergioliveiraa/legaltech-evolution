package br.com.eseg.legaltech.repository;

import br.com.eseg.legaltech.model.EventoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoDocumentoRepository extends JpaRepository<EventoDocumento, Long> {
}