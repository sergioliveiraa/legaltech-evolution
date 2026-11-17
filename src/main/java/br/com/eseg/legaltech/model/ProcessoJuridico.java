package br.com.eseg.legaltech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "processo_juridico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessoJuridico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vinculo_id", nullable = false)
    @NotNull(message = "O vínculo é obrigatório")
    private Vinculo vinculo;

    @Column(name = "area_direito", nullable = false, length = 50)
    private String areaDireito;

    @Column(name = "tipo_processo", nullable = false, length = 50)
    private String tipoProcesso;

    @Column(name = "grau_instancia", length = 30)
    private String grauInstancia;

    @Column(name = "data_abertura", nullable = false)
    private LocalDate dataAbertura;

    @Column(name = "valor_causa", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorCausa;

    @Column(name = "duracao_estimada_dias", nullable = false)
    private Integer duracaoEstimadaDias;

    @Column(name = "status_processo", nullable = false, length = 30)
    private String statusProcesso;

    @Column(name = "score_risco_perda", nullable = false)
    private Double scoreRiscoPerda;

    @Transient
    public String getFaixaRiscoCalculada() {
        if (scoreRiscoPerda == null) return "NAO_DEFINIDO";
        if (scoreRiscoPerda < 0.25) return "BAIXO";
        if (scoreRiscoPerda < 0.50) return "MEDIO";
        if (scoreRiscoPerda < 0.75) return "ALTO";
        return "CRITICO";
    }
}