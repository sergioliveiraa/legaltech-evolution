package br.com.eseg.legaltech.repository;

import br.com.eseg.legaltech.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByVinculoId(Long vinculoId);
}