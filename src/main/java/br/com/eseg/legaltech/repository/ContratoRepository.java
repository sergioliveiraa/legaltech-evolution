package br.com.eseg.legaltech.repository;

import br.com.eseg.legaltech.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    List<Contrato> findByVinculoId(Long vinculoId);
}