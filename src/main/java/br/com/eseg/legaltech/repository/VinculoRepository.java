package br.com.eseg.legaltech.repository;

import br.com.eseg.legaltech.model.Vinculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VinculoRepository extends JpaRepository<Vinculo, Long> {
    List<Vinculo> findByClienteId(Long clienteId);
    List<Vinculo> findByLegaltechId(Long legaltechId);
    Optional<Vinculo> findByClienteIdAndLegaltechIdAndStatusVinculo(Long clienteId, Long legaltechId, Vinculo.StatusVinculo status);
}