package br.com.eseg.legaltech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vinculo_id", nullable = false)
    @NotNull(message = "O vínculo é obrigatório")
    private Vinculo vinculo;

    @Column(name = "tipo_documento", nullable = false, length = 50)
    private String tipoDocumento;

    @Column(name = "area_direito", nullable = false, length = 50)
    private String areaDireito;

    @Column(name = "quantidade_paginas", nullable = false)
    private Integer quantidadePaginas;

    @Column(name = "tempo_analise_seg", nullable = false)
    private Double tempoAnaliseSeg;

    @Column(name = "indice_conformidade", nullable = false)
    private Double indiceConformidade;

    @Column(name = "score_risco_clausula", nullable = false)
    private Double scoreRiscoClausula;

    @OneToMany(mappedBy = "documento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<EventoDocumento> eventos = new ArrayList<>();
}