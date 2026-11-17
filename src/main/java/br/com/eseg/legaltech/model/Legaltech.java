package br.com.eseg.legaltech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "legaltech")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Legaltech {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da legaltech é obrigatório")
    @Column(name = "nome_legaltech", nullable = false, length = 100)
    private String nomeLegaltech;

    @NotBlank(message = "O segmento é obrigatório")
    @Column(name = "segmento", nullable = false, length = 50)
    private String segmento;

    @NotBlank(message = "O porte é obrigatório")
    @Column(name = "porte", nullable = false, length = 30)
    private String porte;

    @Column(name = "ano_fundacao", nullable = false)
    private Integer anoFundacao;

    @NotBlank(message = "O modelo de negócio é obrigatório")
    @Column(name = "modelo_negocio", nullable = false, length = 50)
    private String modeloNegocio;

    @Column(name = "investimento_estimado", nullable = false, precision = 15, scale = 2)
    private BigDecimal investimentoEstimado;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "localidade_id", nullable = false)
    private Localidade localidade;
}