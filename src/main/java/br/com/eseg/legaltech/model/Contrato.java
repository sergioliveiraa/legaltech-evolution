package br.com.eseg.legaltech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contrato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vinculo_id", nullable = false)
    @NotNull(message = "O vínculo é obrigatório")
    private Vinculo vinculo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    @NotNull(message = "O plano é obrigatório")
    private Plano plano;

    @Column(name = "data_inicio", nullable = false)
    @NotNull(message = "A data de início do contrato é obrigatória")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "valor_mensal", nullable = false, precision = 12, scale = 2)
    @NotNull(message = "O valor mensal é obrigatório")
    @Positive(message = "O valor mensal deve ser positivo")
    private BigDecimal valorMensal;

    @Column(name = "data_cancelamento")
    private LocalDate dataCancelamento;

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HistoricoStatusContrato> historicoStatus = new ArrayList<>();

    @Transient
    public boolean isCancelamentoConfirmado() {
        return this.dataCancelamento != null;
    }
}