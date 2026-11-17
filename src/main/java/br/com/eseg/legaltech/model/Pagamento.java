package br.com.eseg.legaltech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vinculo_id", nullable = false)
    @NotNull(message = "O vínculo é obrigatório")
    private Vinculo vinculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id")
    private Contrato contrato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pagamento_origem_id")
    private Pagamento pagamentoOrigem;

    @Column(name = "tipo_receita", nullable = false, length = 30)
    private String tipoReceita;

    @Column(name = "valor_pago", nullable = false, precision = 12, scale = 2)
    @NotNull(message = "O valor pago é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    private BigDecimal valorPago;

    @Column(name = "forma_pagamento", nullable = false, length = 30)
    private String formaPagamento;

    @Column(name = "status_pagamento", nullable = false, length = 20)
    private String statusPagamento;

    @Column(name = "taxa_processamento", nullable = false, precision = 12, scale = 2)
    private BigDecimal taxaProcessamento;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDate dataPagamento;
}