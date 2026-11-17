package br.com.eseg.legaltech.repository;

import br.com.eseg.legaltech.model.Legaltech;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegaltechRepository extends JpaRepository<Legaltech, Long> {
}