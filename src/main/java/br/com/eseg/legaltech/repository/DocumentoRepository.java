package br.com.eseg.legaltech.repository;

import br.com.eseg.legaltech.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByVinculoId(Long vinculoId);
}